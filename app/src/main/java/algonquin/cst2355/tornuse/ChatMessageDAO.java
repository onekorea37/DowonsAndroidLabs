package algonquin.cst2355.tornuse;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChatMessageDAO {

    @Insert
    public long insertMessage(ChatMessage m);

    @Update
    public int anyUpdate(ChatMessage m);

    @Query("Select * From ChatMessage")
    public List<ChatMessage> getAllMessages();

    @Delete
    public int deleteMessage(ChatMessage m);

}
