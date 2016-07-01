package com.example.architecture.bad.myfigurecollection.figuredetail;

import com.example.architecture.bad.myfigurecollection.data.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils.FragmentType;

/**
 * Created by spawn on 29/06/16.
 */
public class FigureDetailFragmentFactory {

    public static FigureDetailFragment createFragmentDetail(@FragmentType int fragmentType, DetailedFigure detailedFigure){

        FigureDetailFragment figureDetailFragment;
        switch (fragmentType){

            case ActivityUtils.OWNED_FRAGMENT:
                figureDetailFragment = FigureDetailOwnedFragment.newInstance(detailedFigure);
                break;
            case ActivityUtils.ORDERED_FRAGMENT:
                figureDetailFragment = FigureDetailOrderedFragment.newInstance(detailedFigure);
                break;
            case ActivityUtils.WISHED_FRAGMENT:
                figureDetailFragment = FigureDetailWishedFragment.newInstance(detailedFigure);
                break;
            default:
                figureDetailFragment = null;
                break;
        }
        return figureDetailFragment;
    }

}
