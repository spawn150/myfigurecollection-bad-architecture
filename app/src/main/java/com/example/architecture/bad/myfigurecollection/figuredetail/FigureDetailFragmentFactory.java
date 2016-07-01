package com.example.architecture.bad.myfigurecollection.figuredetail;

import com.example.architecture.bad.myfigurecollection.data.FigureDetail;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils.FragmentType;

/**
 * Created by spawn on 29/06/16.
 */
public class FigureDetailFragmentFactory {

    public static FigureDetailFragment createFragmentDetail(@FragmentType int fragmentType, FigureDetail figureDetail){

        FigureDetailFragment figureDetailFragment;
        switch (fragmentType){

            case ActivityUtils.OWNED_FRAGMENT:
                figureDetailFragment = FigureDetailOwnedFragment.newInstance(figureDetail);
                break;
            case ActivityUtils.ORDERED_FRAGMENT:
                figureDetailFragment = FigureDetailOrderedFragment.newInstance(figureDetail);
                break;
            case ActivityUtils.WISHED_FRAGMENT:
                figureDetailFragment = FigureDetailWishedFragment.newInstance(figureDetail);
                break;
            default:
                figureDetailFragment = null;
                break;
        }
        return figureDetailFragment;
    }

}
