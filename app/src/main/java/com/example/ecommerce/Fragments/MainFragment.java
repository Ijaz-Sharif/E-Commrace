package com.example.ecommerce.Fragments;

import static com.example.ecommerce.Utils.Constant.getCartData;
import static com.example.ecommerce.Utils.Constant.getProductData;
import static com.example.ecommerce.Utils.Constant.saveCartData;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Model.Product;
import com.example.ecommerce.R;

import java.util.ArrayList;


public class MainFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayAdapter arrayAdapter;
    ArrayList<Product> productArrayList =new ArrayList<Product>();

    static public     ArrayList<Product> productArrayList1 =new ArrayList<Product>();

    private Dialog loadingDialog;
    EditText search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        /////loading dialog
        loadingDialog=new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        search=view.findViewById(R.id.searchProduct);

        recyclerView=view.findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }

            private void filter(String text) {
                ArrayList<Product> filterlist=new ArrayList<>();
                for(Product item: productArrayList){
                    if(item.getName().toLowerCase().contains(text.toLowerCase())){
                        filterlist.add(item);
                    }
                }
                arrayAdapter.filteredList(filterlist);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
         loadingDialog.show();
        productArrayList=getProductData(getContext());
        arrayAdapter=new ArrayAdapter();
        recyclerView.setAdapter(arrayAdapter);
        loadingDialog.dismiss();
        super.onStart();
    }

    public class ArrayAdapter extends RecyclerView.Adapter<ArrayAdapter.ImageViewHoler> {

        public ArrayAdapter(){
            productArrayList1=productArrayList;
        }
        @NonNull
        @Override
        public ArrayAdapter.ImageViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(getContext()).inflate(R.layout.item_product,parent,false);
            return  new ImageViewHoler(v);
        }
        public void filteredList(ArrayList<Product> filterlist) {
            productArrayList1=filterlist;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final ArrayAdapter.ImageViewHoler holder, @SuppressLint("RecyclerView") int position) {


            holder.product_des.setText("Product Description\n"+productArrayList1.get(position).getDescription());
            holder.product_price.setText("Product Price €"+productArrayList1.get(position).getPrice());
            holder.product_name.setText(productArrayList1.get(position).getName());;
            holder.product_img.setImageDrawable(getResources().getDrawable(productArrayList1.get(position).getImage()));
            holder.positive_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     int q= Integer.parseInt(holder.product_quantity.getText().toString());
                     q++;
                    holder.product_quantity.setText(q+"");
                }
            });
            holder.negative_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int q= Integer.parseInt(holder.product_quantity.getText().toString());
                     if(q!=1&&q!=0){
                         q--;
                     }

                    holder.product_quantity.setText(q+"");
                }
            });


            holder.btnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 ArrayList<Product> myCartList= getCartData(getContext());
                   boolean status=false;
                 for(int i=0;i<myCartList.size();i++){
                     if(myCartList.get(i).getProductId()==productArrayList1.get(position).getProductId()){
                         status=true;
                     }
                    }
                 if(!status){
                     myCartList.add(new Product(
                             productArrayList1.get(position).getName()
                             ,productArrayList1.get(position).getPrice()
                             ,productArrayList1.get(position).getDescription()
                             ,productArrayList1.get(position).getImage()
                             ,productArrayList1.get(position).getProductId()
                             ,Integer.parseInt(holder.product_quantity.getText().toString())
                     ));

                     saveCartData(getContext(),myCartList);
                     Toast.makeText(getContext(),"product added in cart list",Toast.LENGTH_LONG).show();
                 }
                 else {
                     Toast.makeText(getContext(),"product already in cart list",Toast.LENGTH_LONG).show();
                 }


                }
            });


        }

        @Override
        public int getItemCount() {
            return productArrayList1.size();
        }

        public class ImageViewHoler extends RecyclerView.ViewHolder {
            ImageView product_img,negative_icon,positive_icon;
            TextView product_name,product_price,product_des,product_quantity;
            CardView cardView;
            Button btnCart;
            public ImageViewHoler(@NonNull View itemView) {
                super(itemView);
                product_img=itemView.findViewById(R.id.product_img);
                product_name=itemView.findViewById(R.id.product_name);
                product_des=itemView.findViewById(R.id.product_des);
                btnCart=itemView.findViewById(R.id.btnCart);
                product_price=itemView.findViewById(R.id.product_price);
                positive_icon=itemView.findViewById(R.id.positive_icon);
                negative_icon=itemView.findViewById(R.id.negative_icon);
                product_quantity=itemView.findViewById(R.id.product_quantity);
            }
        }
    }
}