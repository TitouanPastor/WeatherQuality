package com.example.weathearquality;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import java.util.ArrayList;

public class AdapterHistoriqueList extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> arrayListVille;
    private final ArrayList<String> arrayListDate;
    private final int ressource;

    // Constructeur de la classe AdapterHistoriqueList qui permet d'afficher la liste des villes et des dates
    public AdapterHistoriqueList(Context context, @LayoutRes int ressource, ArrayList<String> arrayListVille, ArrayList<String> arrayListDate) {
        super(context, ressource, arrayListVille);
        this.context = context;
        this.arrayListVille = arrayListVille;
        this.arrayListDate = arrayListDate;
        this.ressource = ressource;
    }

    // Méthode getView qui permet d'afficher les éléments de la liste dans la vue
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(this.ressource, parent, false);

        TextView textVille = rowView.findViewById(R.id.textVille);
        TextView textDate = rowView.findViewById(R.id.textDate);
        textVille.setText(arrayListVille.get(position));
        textDate.setText(arrayListDate.get(position));
        return rowView;
    }

}
