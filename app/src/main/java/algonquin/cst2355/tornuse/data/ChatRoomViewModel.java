package algonquin.cst2355.tornuse.data;


import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2355.tornuse.ChatMessage;

public class ChatRoomViewModel extends ViewModel {

    public ArrayList<ChatMessage> messages = new ArrayList<>();

}
