package algonquin.cst2355.tornuse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import algonquin.cst2355.tornuse.ChatMessage;

import androidx.fragment.app.Fragment;

import com.example.algonquin.cst2355.tornuse.databinding.DetailsLayoutBinding;


public class MessageDetailsFragment extends Fragment {

    ChatMessage thisMessage;

    public MessageDetailsFragment(ChatMessage toShow) {
        thisMessage = toShow;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle instance) {
        super.onCreateView(inflater, parent, instance);

        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);

        binding.messageText.setText( thisMessage.getMessage());
        binding.timeText.setText( thisMessage.getTimeSent());
        binding.idText.setText( Long.toString(thisMessage.getId()));

        return binding.getRoot();
    }
}
