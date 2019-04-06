package com.online.music.mic.utils;

import com.online.music.mic.model.compatition_level_rank_responce.Participant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriceProductSorter {

    List<Participant> jobCandidate = new ArrayList<>();

    public PriceProductSorter(List<Participant> jobCandidate) {
        this.jobCandidate = jobCandidate;
    }

    public List<Participant> getSortedProductByHightoLow() {
        Collections.sort(jobCandidate, Participant.hightolowComparator);
        return jobCandidate;
    }



}
