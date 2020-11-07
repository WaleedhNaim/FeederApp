package com.example.nb_interview.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nb_interview.R;
import com.example.nb_interview.model.Products;

import java.util.ArrayList;
import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Products> productList;
    private Activity mActivity;

    public ProductAdapter(Activity mActivity) {
        this.mActivity = mActivity;
        productList = new ArrayList<>();
    }

    /*
    Method to set all the data to adapter
     */
    public void setData(List<Products> productList) {
        this.productList.clear();
        this.productList.addAll(productList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_product, parent, false);
        ProductAdapter.ProductViewHolder pvh = new ProductAdapter.ProductViewHolder(itemView);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, final int position) {

        Products product = productList.get(position);
        holder.lbl_id.setText(product.getId());
        holder.lbl_name.setText(product.getName());
        holder.lbl_description.setText(product.getDescription());

        Glide.with(mActivity)
                .load(product.getImage())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .dontAnimate()
                .into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView lbl_id, lbl_name, lbl_description;
        ImageView imageView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            lbl_id = itemView.findViewById(R.id.lbl_id);
            lbl_name = itemView.findViewById(R.id.lbl_name);
            lbl_description = itemView.findViewById(R.id.lbl_description);
            imageView = itemView.findViewById(R.id.image);

        }
    }

    /*
    Method to get the selected data by passing the position
     */
    public Products getProduct(int position) {
        return productList.get(position);
    }

    /*
    Method to remove the selected data by passing the position
     */
    public void removeItem(int position) {
        productList.remove(position);
        notifyItemRemoved(position);
    }
}