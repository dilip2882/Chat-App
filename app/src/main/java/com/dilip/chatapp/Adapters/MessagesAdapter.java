package com.dilip.chatapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dilip.chatapp.Models.Message;
import com.dilip.chatapp.R;
import com.dilip.chatapp.databinding.ItemReceiveBinding;
import com.dilip.chatapp.databinding.ItemSentBinding;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfig;
import com.github.pgreze.reactions.ReactionsConfigBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MessagesAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Message> messages;

    final int ITEM_SENT = 1;
    final int ITEM_RECEIVE = 2;

    String senderRoom;
    String receiverRoom;

    public MessagesAdapter(Context context, ArrayList<Message> messages, String senderRoom, String receiverRoom) {
        this.context = context;
        this.messages = messages;
        this.senderRoom = senderRoom;
        this.receiverRoom = receiverRoom;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_sent, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_receive, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if(FirebaseAuth.getInstance().getUid().equals(message.getSenderId())) {
            return ITEM_SENT;
        } else {
            return ITEM_RECEIVE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);

        int[] reactions = new int[]{
                R.drawable.ic_fb_like,
                R.drawable.ic_fb_love,
                R.drawable.ic_fb_laugh,
                R.drawable.ic_fb_wow,
                R.drawable.ic_fb_sad,
                R.drawable.ic_fb_angry
        };

        ReactionsConfig config = new ReactionsConfigBuilder(context)
                .withReactions(reactions)
                .build();

        ReactionPopup popup = new ReactionPopup(context, config, (pos) -> {

            if(pos < 0) {
                return false;
            }

            if(holder.getClass() == SentViewHolder.class) {
                SentViewHolder viewHolder = (SentViewHolder)holder;
                viewHolder.binding.feeling.setImageResource(reactions[pos]);
                viewHolder.binding.feeling.setVisibility(View.VISIBLE);
            } else {
                ReceiverViewHolder viewHolder = (ReceiverViewHolder)holder;
                viewHolder.binding.feeling.setImageResource(reactions[pos]);
                viewHolder.binding.feeling.setVisibility(View.VISIBLE);

            }
            message.setFeeling(pos);

            // Update both sender and receiver message nodes in Firebase with the latest message data
  /*          FirebaseDatabase.getInstance().getReference()
                    .child("chats")
                    .child(senderRoom)
                    .child("messages")
                    .child(message.getMessageId()).setValue(message);

            FirebaseDatabase.getInstance().getReference()
                    .child("chats")
                    .child(receiverRoom)
                    .child("messages")
                    .child(message.getMessageId()).setValue(message);*/   // Gives NullPointerException

            String senderRef = "chats/" + senderRoom + "/messages/" + message.getMessageId();
            String receiverRef = "chats/" + receiverRoom + "/messages/" + message.getMessageId();

            // Save message to sender's room
            if (senderRef != null) {
                FirebaseDatabase.getInstance().getReference().child(senderRef).setValue(message);
            }

            // Save message to receiver's room
            if (receiverRef != null) {
                FirebaseDatabase.getInstance().getReference().child(receiverRef).setValue(message);
            }

            return true; // true is closing popup, false is requesting a new selection
        });


        // Check the ViewHolder type (Sent or Receiver)
        if (holder.getClass() == SentViewHolder.class) {
            // Cast the holder to SentViewHolder for specific binding access
            SentViewHolder viewHolder = (SentViewHolder) holder;

            // Set the message text
            viewHolder.binding.message.setText(message.getMessage());

            // Check if the message has a feeling set (position >= 0)
            if (message.getFeeling() >= 0) {
//                message.setFeeling(reactions[(int) message.getFeeling()]);
                // Update the feeling image based on the position stored in the message
                viewHolder.binding.feeling.setImageResource(reactions[(int) message.getFeeling()]);
                // Make the feeling image visible
                viewHolder.binding.feeling.setVisibility(View.VISIBLE);
            } else {
                // Hide the feeling image if no feeling is set
                viewHolder.binding.feeling.setVisibility(View.GONE);
            }

            // Set a touch listener on the message view
            viewHolder.binding.message.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Delegate touch handling to the popup for reaction selection
                    popup.onTouch(v, event);
                    // Consume the touch event to prevent further processing
                    return false;
                }
            });

        } else {
            ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;
            viewHolder.binding.message.setText(message.getMessage());

            if (message.getFeeling() >= 0) {
                viewHolder.binding.feeling.setImageResource(reactions[(int) message.getFeeling()]);
                viewHolder.binding.feeling.setVisibility(View.VISIBLE);
            } else {
                viewHolder.binding.feeling.setVisibility(View.GONE);
            }

            viewHolder.binding.message.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popup.onTouch(v, event);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class SentViewHolder extends RecyclerView.ViewHolder {

        ItemSentBinding binding;
        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemSentBinding.bind(itemView);
        }
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        ItemReceiveBinding binding;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemReceiveBinding.bind(itemView);
        }
    }

}