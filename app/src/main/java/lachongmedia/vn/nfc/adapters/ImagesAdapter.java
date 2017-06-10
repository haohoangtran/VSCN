package lachongmedia.vn.nfc.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.adapters.viewholders.ImageViewHolder;
import lachongmedia.vn.nfc.database.DbContext;

/**
 * Created by tranh on 5/19/2017.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    List<String> pathList;

    public ImagesAdapter(List<String> pathList) {
        this.pathList = pathList;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = android.view.LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.image_itemview, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(itemView);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.bind(pathList.get(position));
    }

    @Override
    public int getItemCount() {
        return pathList.size();
    }
}
