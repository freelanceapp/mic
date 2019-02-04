package com.mic.music.mic.model;

import com.mic.music.mic.model.competition_responce.CompletionModel;
import com.mic.music.mic.model.otp_responce.OtpModel;

public class Compatition1 {

    public static CompletionModel completionModel;

    public static CompletionModel getCompletionModel() {
        return completionModel;
    }

    public static void setCompletionModel(CompletionModel completionModel) {
        Compatition1.completionModel = completionModel;
    }
}
