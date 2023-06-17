package algonquin.cst2355.tornuse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.algonquin.cst2355.tornuse.R;
import com.example.algonquin.cst2355.tornuse.databinding.ActivityChatRoomBinding;
import com.example.algonquin.cst2355.tornuse.databinding.ReceiveMessageBinding;
import com.example.algonquin.cst2355.tornuse.databinding.SentMessageBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2355.tornuse.data.ChatRoomViewModel;

public class ChatRoom extends AppCompatActivity {

    protected ArrayList<ChatMessage> messages;
    ActivityChatRoomBinding binding;

    RecyclerView.Adapter<MyRowHolder> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ChatRoomViewModel chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        messages = chatModel.messages;

        binding.submit.setOnClickListener(click -> {
            String typed = binding.message.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());

            messages.add(new ChatMessage(typed, currentDateandTime, true));

            myAdapter.notifyItemInserted(messages.size() - 1);

            binding.message.setText(""); // remove what was typed

        });

        binding.receive.setOnClickListener(click -> {
            String typed = binding.message.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());

            messages.add(new ChatMessage(typed, currentDateandTime, false));

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

    public class MyRowHolder extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.theMessage);
            timeText = itemView.findViewById(R.id.theTime);

        }
    }
}
