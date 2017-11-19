package kmitl.natcha58070069.com.libreria;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

//-------------------------- Can Delete Page ------------------------------------//

/**
 * A simple {@link Fragment} subclass.
 */
public class FrontCoverFragment extends Fragment implements View.OnClickListener{

    private Button fmNextPage;

    private FrontCoverFragmentListener listener;

    public FrontCoverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listener = (FrontCoverFragmentListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_front_cover, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ประกาศในนี้ก็ไม่รกดีนะ
        fmNextPage = view.findViewById(R.id.fmNextPage);
        fmNextPage.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.fmNextPage:
             listener.onFmNextPageClick();
             break;
     }
    }

    public interface FrontCoverFragmentListener {
        void onFmNextPageClick();
    }
}
