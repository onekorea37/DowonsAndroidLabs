package algonquin.cst2355.tornuse;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.algonquin.cst2355.tornuse.databinding.DetailsLayoutBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MessageDetailsFragment extends Fragment {

    ChatMessage thisMessage;
    ChatMessageDAO myDAO;
    OnMessageDeletedListener messageDeletedListener;

    public MessageDetailsFragment(ChatMessage toShow, ChatMessageDAO dao) {
        thisMessage = toShow;
        myDAO = dao;
    }

    public void setOnMessageDeletedListener(OnMessageDeletedListener listener) {
        this.messageDeletedListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle instance) {
        super.onCreateView(inflater, parent, instance);

        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);

        binding.messageText.setText(thisMessage.getMessage());
        binding.timeText.setText(thisMessage.getTimeSent());
        binding.idText.setText(Long.toString(thisMessage.getId()));

        // Return the root view
        return binding.getRoot();
    }

    void deleteCurrentMessage() {
        // Delete the current message from the database
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            myDAO.deleteMessage(thisMessage);

            // Notify the activity that the message has been deleted
            if (messageDeletedListener != null) {
                messageDeletedListener.onMessageDeleted(thisMessage);
            }
        });

        // Close the fragment
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    public interface OnMessageDeletedListener {
        void onMessageDeleted(ChatMessage message);
    }
}
