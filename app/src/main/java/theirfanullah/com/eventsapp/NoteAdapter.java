package theirfanullah.com.eventsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Irfan Ullah on 08/01/2018.
 */

public class NoteAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> lsId;
    ArrayList<String> lsNoteTitle;
    ArrayList<String> lsNoteDescription;
    ArrayList<String> lsDate;
    public NoteAdapter(@NonNull Context context, ArrayList<String> Id,ArrayList<String> title, ArrayList<String> desc, ArrayList<String> dat) {
        super(context, R.layout.custom_note_layout, title);
        this.context = context;
        this.lsId = Id;
        this.lsNoteTitle = title;
        this.lsNoteDescription = desc;
        this.lsDate = dat;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View custom = inflater.inflate(R.layout.custom_note_layout,null,false);
        TextView title = custom.findViewById(R.id.ntitle);
        TextView desc = custom.findViewById(R.id.ndesc);
        TextView datee = custom.findViewById(R.id.ndate);
        TextView idd = custom.findViewById(R.id.nId);
        String ntitle = getItem(position);
        title.setText(ntitle);
        desc.setText(lsNoteDescription.get(position));
        datee.setText(lsDate.get(position));
        idd.setText(lsId.get(position));
        idd.setVisibility(View.GONE);


        return custom;
    }
}
