package com.melapelapp.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.melapelapp.DownloadImageTask;
import com.melapelapp.R;
import com.melapelapp.domain.Person;

import java.util.ArrayList;
import java.util.List;

//RecyclerView

//ListView public class MySimpleArrayAdapter extends ArrayAdapter<String> {
public class PersonArrayAdapter extends RecyclerView.Adapter<PersonArrayAdapter.ViewHolder> {
    PinterestView pinterestView;
    private List<Person> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView imgView;
        public ViewHolder(View v) {
            super(v);
            imgView = (ImageView) v.findViewById(R.id.icon);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);

            //String url="http://www.jonathangama.com/liceo/documentos/retratos/85344.jpg";


        }
    }

//   private Bitmap getImage(String url)
//   {
//       //String urldisplay = urls[0];
//       Bitmap mIcon11 = null;
//       try {
//           InputStream in = new URL(url).openStream();
//           mIcon11 = BitmapFactory.decodeStream(in);
//       } catch (Exception e) {
//           //Log.e("Error", e.getMessage());
//           e.printStackTrace();
//       }
//       return mIcon11;
//   }

    public void add(int position, String item) {
      //fix later  mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Person item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }






    public PersonArrayAdapter(List<Person> myDataset, PinterestView pinterestView) {
        mDataset = myDataset;
        this.pinterestView=pinterestView;
    }

    @Override
    public PersonArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_card_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PersonArrayAdapter.ViewHolder holder, int position) {
// - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Person name = mDataset.get(position);
        holder.txtHeader.setText(mDataset.get(position).getName());
        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        remove(name);
                    }
                });

        holder.txtFooter.setText(mDataset.get(position).getLastName());

        String url="http://www.jonathangama.com/liceo/documentos/retratos/" + mDataset.get(position).getId() +".jpg";

        //Bitmap image = getImage(url);
        //imgView.setImageBitmap(image);

        DownloadImageTask downloadImageTask;
        downloadImageTask= new DownloadImageTask(url,holder.imgView);
        downloadImageTask.execute(url);

        holder.imgView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pinterestView.dispatchTouchEvent(event);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}