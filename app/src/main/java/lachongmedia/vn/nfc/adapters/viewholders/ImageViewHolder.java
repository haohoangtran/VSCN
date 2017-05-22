package lachongmedia.vn.nfc.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import lachongmedia.vn.nfc.R;

/**
 * Created by tranh on 5/19/2017.
 */

public class ImageViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.iv_remove)
    ImageView ivRemove;

    public ImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(String path) {
        Picasso.with(itemView.getContext()).load("file://" + path).into(ivImage);
    }
}
