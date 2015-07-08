package com.searun.GIS.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.searun.GIS.R;
import com.searun.GIS.interfacepackage.OnFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MenuRightFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ListView operateStep;
    public MenuRightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.right_menu,container,false);
        findView(view);
        return view;
    }

    private void findView(View view){
        operateStep = (ListView) view.findViewById(R.id.operate_step_lv);
        operateStep.setAdapter(new ArrayAdapter<>(getActivity(),android.R.layout.simple_expandable_list_item_1,getList()));
        operateStep.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    mListener.onFragmentInteraction(getList().get(position));
                }
            }
        });
    }
    private List<String> getList(){
        List<String> strings = new ArrayList<>();
        strings.add("装车到场");
        strings.add("装车确认");
        strings.add("发车确认");
        strings.add("在途跟踪");
        strings.add("卸货到达");
        strings.add("回单确认");
        strings.add("回单签收");
        return strings;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String string) {
        if (mListener != null) {
            mListener.onFragmentInteraction(string);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
