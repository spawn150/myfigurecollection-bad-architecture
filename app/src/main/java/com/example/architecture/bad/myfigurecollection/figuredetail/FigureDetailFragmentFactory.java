package com.example.architecture.bad.myfigurecollection.figuredetail;

import com.example.architecture.bad.myfigurecollection.data.ItemFigureDetail;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils.FragmentType;

/**
 * Created by spawn on 29/06/16.
 */
public class FigureDetailFragmentFactory {

    public static FigureDetailFragment createFragmentDetail(@FragmentType int fragmentType, ItemFigureDetail itemFigureDetail){

        FigureDetailFragment figureDetailFragment;
        switch (fragmentType){

            case ActivityUtils.OWNED_FRAGMENT:
                figureDetailFragment = FigureDetailOwnedFragment.newInstance(itemFigureDetail);
                break;
            case ActivityUtils.ORDERED_FRAGMENT:
                figureDetailFragment = FigureDetailOrderedFragment.newInstance(itemFigureDetail);
                break;
            case ActivityUtils.WISHED_FRAGMENT:
                figureDetailFragment = FigureDetailWishedFragment.newInstance(itemFigureDetail);
                break;
            default:
                figureDetailFragment = null;
                break;
        }
        return figureDetailFragment;
    }

}
