package itp341.mai.johnathan.koazie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Formatter;


public class ResultsFragment extends Fragment {

    public static final String ARG_LOCATION = "itp341.mai.johnathan.koazie.resultname.arg";
    public static final String ARG_LAT = "itp341.mai.johnathan.koazie.resultlat.arg";
    public static final String ARG_LONG = "itp341.mai.johnathan.koazie.resultlong.arg";

    private String resultLocation = "";
    private double resultLatitude = 0.0;
    private double resultLongitude = 0.0;


    public ResultsFragment() {
        // Required empty public constructor
    }

    public static ResultsFragment newInstance(String location, double latitude, double longitude) {
        ResultsFragment fragment = new ResultsFragment();

        // Putting information into bundle for fragment
        Bundle args = new Bundle();
        args.putString(ARG_LOCATION, location);
        args.putDouble(ARG_LAT, latitude);
        args.putDouble(ARG_LONG, longitude);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultLocation = getArguments().getString(ARG_LOCATION);
        resultLatitude = getArguments().getDouble(ARG_LAT);
        resultLongitude = getArguments().getDouble(ARG_LONG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Setting title as location
        //getActivity().getActionBar().setTitle(resultLocation);

        View v = inflater.inflate(R.layout.fragment_results, container, false);

        return v;
    }
}

class ResultAdapter extends ArrayAdapter<Result> {
    public ResultAdapter(Context context, ArrayList<Result> notes) {
        super(context, 0, notes);
    }
    private final String TAG = ResultsFragment.class.getName();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Result note = NoteSingleton.get(getContext()).getNote(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        return convertView;
    }
}
