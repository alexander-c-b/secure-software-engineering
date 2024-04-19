package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PastScoreAdapter extends ArrayAdapter<PastScore> {

    private List<PastScore> pastScores;

    public PastScoreAdapter(Context context, List<PastScore> pastScores) {
        super(context, 0, pastScores);
        this.pastScores = pastScores;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_past_score, parent, false);
        }

        PastScore currentScore = pastScores.get(position);

        TextView scoreTextView = listItemView.findViewById(R.id.scoreTextView);
        scoreTextView.setText("Score: " + currentScore.correctAnswers + "/" + currentScore.totalQuestions);

        return listItemView;
    }
}
