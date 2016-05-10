package itp341.mai.johnathan.koazie;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.loopj.android.http.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;


public class ResultsFragment extends Fragment {

    // Constants
    public static final String ARG_LOCATION = "itp341.mai.johnathan.koazie.resultname.arg";
    public static final String ARG_LAT = "itp341.mai.johnathan.koazie.resultlat.arg";
    public static final String ARG_LONG = "itp341.mai.johnathan.koazie.resultlong.arg";
    private final String TAG = ResultsFragment.class.getName();

    static final String kFacilityName = "provider_name";
    static final String kPhoneNumber1 = "provider_phone_number";
    static final String kPhoneNumber2 = "phone_number";
    static final String kBeds = "number_of_certified_beds";
    static final String kRating = "overall_rating";
    static final String kAddress = "provider_address";
    static final String kCity = "provider_city";
    static final String kState = "provider_state";
    static final String kZipCode = "provider_zip_code";
    static final String kProviderNumber = "federal_provider_number";

    // Arguments for fragment
    private String resultLocation = "";
    private double resultLatitude = 0.0;
    private double resultLongitude = 0.0;
    private int resultRadius = 4 * 1609; // Represents search radius from given address, a default of a 4 mile radius converted to meters.

    // Widgets
    TextView mTextLocationTitle;
    ListView mListViewResults;

    // Misc
    RequestQueue queue;
    ArrayList<Result> resultsArray;
    String jsonRequestURL;

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

        // JSON Request Stuff
        jsonRequestURL = "https://data.medicare.gov/resource/hq9i-23gr.json?$where=within_circle(location,%20"
                + String.valueOf(resultLatitude)
                + ",%20"
                + String.valueOf(resultLongitude)
                + ",%20"
                + String.valueOf(resultRadius)
                + ")";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Setting title as location
        //getActivity().getActionBar().setTitle(resultLocation);

        View v = inflater.inflate(R.layout.fragment_results, container, false);
        mTextLocationTitle = (TextView) v.findViewById(R.id.textLocationTitle);
        mListViewResults = (ListView) v.findViewById(R.id.listViewResults);

        mTextLocationTitle.setText("Results in " + resultLocation);

        // SEtting custom adapter
        /*
        resultsArray = new ArrayList<Result>();
        for (int i = 0; i < 5; i++) {
            Result r = new Result();
            resultsArray.add(r);
        }

        ResultAdapter adapter = new ResultAdapter(getActivity(), resultsArray);
        mListViewResults.setAdapter(adapter);*/

        // Sends network call to populate custom adapter
        new RetrieveJsonTask().execute(jsonRequestURL);

        return v;
    }

    // Custom adapter class
    class ResultAdapter extends ArrayAdapter<Result> {
        public ResultAdapter(Context context, ArrayList<Result> results) {
            super(context, 0, results);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            TextView facilityIndex = (TextView) convertView.findViewById(R.id.textViewIndex);
            TextView facilityName = (TextView) convertView.findViewById(R.id.textViewName);
            TextView facilityAddress1 = (TextView) convertView.findViewById(R.id.textViewAddress1);
            TextView facilityAddress2 = (TextView) convertView.findViewById(R.id.textViewAddress2);
            TextView facilityPhone = (TextView) convertView.findViewById(R.id.textViewPhone);
            TextView facilityRating = (TextView) convertView.findViewById(R.id.textViewRating);
            TextView facilityBeds = (TextView) convertView.findViewById(R.id.textViewBeds);

            facilityIndex.setText(String.valueOf(position + 1) + ".");
            facilityName.setText(resultsArray.get(position).getName());
            facilityAddress1.setText(resultsArray.get(position).getAddress1());
            facilityAddress2.setText(resultsArray.get(position).getAddress2());
            facilityPhone.setText(resultsArray.get(position).getPhone());
            facilityRating.setText(resultsArray.get(position).getRating());
            facilityBeds.setText(resultsArray.get(position).getBeds());

            return convertView;
        }
    }

    // Asynctask to retrieve JSON data
    class RetrieveJsonTask extends AsyncTask<String, Void, JSONArray> {
        private Exception e;

        protected JSONArray doInBackground(String... urls) {
            // Network call
            StringBuilder response = new StringBuilder("");
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                int status = httpURLConnection.getResponseCode();
                Log.d(TAG, String.valueOf(status));
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null)
                    response.append(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(TAG, response.toString());

            try {
                JSONArray jsonArray = (JSONArray) new JSONTokener(response.toString()).nextValue();
                return jsonArray;
            } catch (JSONException e) {
            }

            return null;
        }

        // Parses JSON Data here
        protected void onPostExecute(JSONArray data) {
            if (data != null) {
                resultsArray = new ArrayList<Result>();

                for (int i = 0; i < data.length(); i++) {
                    try {
                        JSONObject object = data.getJSONObject(i);
                        String name = object.getString(kFacilityName);
                        String address1 = object.getString(kAddress);
                        String city = object.getString(kCity);
                        String state = object.getString(kState);
                        String zip = object.getString(kZipCode);
                        String address2 = city + ", " + state + " " + zip;
                        JSONObject phone1 = object.getJSONObject(kPhoneNumber1);
                        String phone2 = phone1.getString(kPhoneNumber2);
                        String rating = object.getString(kRating);
                        String beds = object.getString(kBeds);

                        Result r = new Result(name, address1, address2, phone2, rating, beds);
                        resultsArray.add(r);

                    } catch (JSONException e) {
                    }
                }

                Log.d(TAG, String.valueOf(resultsArray.size()));
                for (int i = 0; i < resultsArray.size(); i++) {
                    Log.d(TAG, resultsArray.get(i).toString());
                }

                // Setting adapter to listview
                ResultAdapter adapter = new ResultAdapter(getActivity(), resultsArray);
                mListViewResults.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
