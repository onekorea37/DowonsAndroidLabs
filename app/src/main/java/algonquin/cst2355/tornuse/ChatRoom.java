package algonquin.cst2355.tornuse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.algonquin.cst2355.tornuse.R;
import com.example.algonquin.cst2355.tornuse.databinding.ActivityChatRoomBinding;
import com.example.algonquin.cst2355.tornuse.databinding.ReceiveMessageBinding;
import com.example.algonquin.cst2355.tornuse.databinding.SentMessageBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2355.tornuse.data.ChatRoomViewModel;

public class ChatRoom extends AppCompatActivity {

    protected ArrayList<ChatMessage> messages;
    ActivityChatRoomBinding binding;
    MessageDatabase myDB;
    RecyclerView.Adapter<MyRowHolder> myAdapter;

    ChatMessageDAO myDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ChatRoomViewModel chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        messages = chatModel.messages;

        myDB = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        myDAO = myDB.cmDAO();

        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(new Runnable() {
            @Override
            public void run() {
                List<ChatMessage> allMessages = myDAO.getAllMessages();

                messages.addAll(allMessages);

                if(myAdapter != null)
                    myAdapter.notifyDataSetChanged();
            }
        });

        binding.submit.setOnClickListener(click -> {
            String typed = binding.message.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());

            ChatMessage newMessage = new ChatMessage(typed, currentDateandTime, true);
            Executor thread1 = Executors.newSingleThreadExecutor();
            thread1.execute(new Runnable() {
                @Override
                public void run() {
                    newMessage.id = myDAO.insertMessage(newMessage);
                }
            });

            messages.add(newMessage);

            myAdapter.notifyItemInserted(messages.size() - 1);

            binding.message.setText(""); // remove what was typed

        });

        binding.receive.setOnClickListener(click -> {
            String typed = binding.message.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());

            ChatMessage newMessage = new ChatMessage(typed, currentDateandTime, false);
            Executor thread2 = Executors.newSingleThreadExecutor();
            thread2.execute(new Runnable() {
                @Override
                public void run() {
                    newMessage.id = myDAO.insertMessage(newMessage);
                }
            });

            messages.add(newMessage);

            myAdapter.notifyItemInserted(messages.size() - 1);

            binding.message.setText(""); // remove what was typed

        });

        binding.theRecyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);

                    // this will initialize the row variables
                    return new MyRowHolder(binding.getRoot());
                } else {
                    ReceiveMessageBinding rowBinding = ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(rowBinding.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {

                ChatMessage message = messages.get(position);

                holder.messageText.setText(message.getMessage());
                holder.timeText.setText(message.getTimeSent());

            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                ChatMessage message = messages.get(position);
                return message.isSend() ? 0 : 1;
            }
        });

        binding.theRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    protected class MyRowHolder extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.theMessage);
            timeText = itemView.findViewById(R.id.theTime);

            itemView.setOnClickListener(click -> {

                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setMessage("Do you really want to delete this?");

                builder.setTitle("Question");

                builder.setPositiveButton("No", (cl, which) -> {
                    Log.d("DELETE", "The user clicked NO");
                });

                builder.setNegativeButton("Yes", (cl, which) -> {
                    Log.d("DELETE", "The user clicked YES");
                    int whichRowClicked = getAbsoluteAdapterPosition();
                    ChatMessage cm = messages.get(whichRowClicked);

                    Executor thread = Executors.newSingleThreadExecutor();
                    thread.execute(() -> {
                        myDAO.deleteMessage(cm);
                        messages.remove(whichRowClicked);

                        runOnUiThread(() -> {
                            myAdapter.notifyDataSetChanged();
                        });

                        Snackbar.make(messageText, "You deleted message #" + whichRowClicked, Snackbar.LENGTH_LONG)
                                .setAction("Undo", clk ->{

                                    messages.add(whichRowClicked, cm);
                                    myAdapter.notifyItemRemoved(whichRowClicked);
                                })
                                .show();
                    });
                });

                builder.create().show();
            });
        }
    }
}
