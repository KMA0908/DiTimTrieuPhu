package com.example.ditimtrieuphu.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.ditimtrieuphu.database.FirebaseDatabaseManager;
import com.example.ditimtrieuphu.dto.PlayerInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class HighScoreViewModel extends ViewModel {
    private MutableLiveData<List<PlayerInfo>> mPlayerInfoMutableLiveData;

    public HighScoreViewModel() {
        mPlayerInfoMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<PlayerInfo>> getmPlayerInfoMutableLiveData() {
        return mPlayerInfoMutableLiveData;
    }

    public void syncDataHighScore() {
        // sync data cua high score
        FirebaseDatabaseManager.getInstance().getAllUserInfoSortedByProperty().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                List<PlayerInfo> playerInfoList = new ArrayList<>();
                for (DocumentSnapshot d : snapshots) {
                    PlayerInfo playerInfo = d.toObject(PlayerInfo.class);
                    playerInfoList.add(playerInfo);
                }
                mPlayerInfoMutableLiveData.postValue(playerInfoList);
            } else {

            }
        }).addOnFailureListener(e -> {
            // Xử lý lỗi ở đây
        });

    }
}
