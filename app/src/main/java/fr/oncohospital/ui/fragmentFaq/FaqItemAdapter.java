package fr.oncohospital.ui.fragmentFaq;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.oncohospital.R;
import fr.oncohospital.model.allData.data.QuestionEntity;

/**
 * Created by Assia HACHICHI on 06/03/2021
 */
public class FaqItemAdapter extends RecyclerView.Adapter<FaqItemAdapter.QuestionViewHolder> {

    private OnItemClickListener mListener;
    private List<QuestionEntity> allQuestions=new ArrayList<QuestionEntity>();

    public void setAllQuestions(List<QuestionEntity> allQuestions) {
        this.allQuestions = allQuestions;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_question_item, parent, false);
        return new QuestionViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        QuestionEntity currentQuestion = allQuestions.get(position);
        holder.mTextView1.setText(currentQuestion.getQuestion());
    }

    @Override
    public int getItemCount() {
        if (allQuestions == null) return 0;
        return allQuestions.size();
    }





    public interface OnItemClickListener{
        void onItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;

        public QuestionViewHolder(View itemView, OnItemClickListener listener){
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.question_item_textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position !=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }


}
