package itp341.mai.johnathan.koazie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;


import java.util.ArrayList;


public class FavoritesFragment extends Fragment {

    ListView mListViewFavorites;

    ArrayList<Result> arrayFavorites;
    FavoritesAdapter adapter;


    public FavoritesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayFavorites = FavoritesSingleton.get(getActivity().getApplicationContext()).getFavorites();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_favorites, container, false);

        mListViewFavorites = (ListView) v.findViewById(R.id.listViewFavorites);
        adapter = new FavoritesAdapter(getActivity(), arrayFavorites);
        mListViewFavorites.setAdapter(adapter);

        return v;
    }

    class FavoritesAdapter extends ArrayAdapter<Result> {
        public FavoritesAdapter(Context context, ArrayList<Result> favorites) {
            super(context, 0, favorites);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_favorites, parent, false);
            }

            TextView facilityIndex = (TextView) convertView.findViewById(R.id.textViewFavoritesIndex);
            TextView facilityName = (TextView) convertView.findViewById(R.id.textViewFavoritesName);
            TextView facilityPhone = (TextView) convertView.findViewById(R.id.textViewFavoritesPhone);
            Button buttonRemove = (Button) convertView.findViewById(R.id.buttonRemove);

            facilityIndex.setText(String.valueOf(position + 1) + ".");
            facilityName.setText(arrayFavorites.get(position).getName());
            facilityPhone.setText(arrayFavorites.get(position).getPhone());

            buttonRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FavoritesSingleton.get(getActivity().getApplicationContext()).removeFavorite(position);
                    adapter.notifyDataSetChanged();
                }
            });

            return convertView;
        }
    }
}
