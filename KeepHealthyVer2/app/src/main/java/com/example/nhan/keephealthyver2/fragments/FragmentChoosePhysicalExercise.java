package com.example.nhan.keephealthyver2.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.example.nhan.keephealthyver2.R;
import com.example.nhan.keephealthyver2.adapters.ChooseBreathRecycleViewAdapter;
import com.example.nhan.keephealthyver2.adapters.ChoosePhysicalRecycleViewAdapter;
import com.example.nhan.keephealthyver2.database.RealmHandler;
import com.example.nhan.keephealthyver2.events.EventDataReady;
import com.example.nhan.keephealthyver2.events.EventSendBreathObject;
import com.example.nhan.keephealthyver2.events.EventSendPhysicalObject;
import com.example.nhan.keephealthyver2.models.BreathRealmObject;
import com.example.nhan.keephealthyver2.models.PhysicalRealmObject;
import com.example.nhan.keephealthyver2.models.StringRealmObject;
import com.example.nhan.keephealthyver2.networks.ApiUrl;
import com.example.nhan.keephealthyver2.networks.GetBreathExerciseFromAPI;
import com.example.nhan.keephealthyver2.networks.GetPhysicalExerciseFromAPI;
import com.example.nhan.keephealthyver2.networks.ServiceFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nhan on 10/15/2016.
 */

public class FragmentChoosePhysicalExercise extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private ChoosePhysicalRecycleViewAdapter adapter;
    private ServiceFactory serviceFactory;
    private PhysicalRealmObject physicalObject;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_choose_breath_exercise, container, false);

        EventBus.getDefault().register(this);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycle_view_breath);
        layoutManager = new GridLayoutManager(view.getContext(),
                2,
                GridLayout.VERTICAL,
                false);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 3 == 0 ? 2 : 1);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        loadDataByRetrofit();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void setAdapterView(EventDataReady event){
        adapter = new ChoosePhysicalRecycleViewAdapter();
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void loadDataByRetrofit(){
        if (RealmHandler.getInstance().getListBreathObjectFromRealm().size() == 0) {
            serviceFactory = new ServiceFactory(ApiUrl.BASE_URL);
            GetPhysicalExerciseFromAPI service = serviceFactory.createService(GetPhysicalExerciseFromAPI.class);
            Call<GetPhysicalExerciseFromAPI.Physical> call = service.callPhysicalExercise();
            call.enqueue(new Callback<GetPhysicalExerciseFromAPI.Physical>() {
                @Override
                public void onResponse(Call<GetPhysicalExerciseFromAPI.Physical> call, Response<GetPhysicalExerciseFromAPI.Physical> response) {
                    RealmHandler.getInstance().clearDataInRealm();
                    List<GetPhysicalExerciseFromAPI.ExercisePhysical> exercisePhysicalList = response.body().getExercisePhysicalList();

                    for (int i = 0; i < exercisePhysicalList.size(); i++){
                        PhysicalRealmObject physicalObject = new PhysicalRealmObject();
                        physicalObject.setName(exercisePhysicalList.get(i).getName());
                        physicalObject.setId(exercisePhysicalList.get(i).getId());
                        physicalObject.setColor(exercisePhysicalList.get(i).getColor());
                        physicalObject.setImage(exercisePhysicalList.get(i).getImage());
                        physicalObject.setLinkYoutube(exercisePhysicalList.get(i).getLinkYoutube());

                        RealmHandler.getInstance().addPhysicalObjectToRealm(physicalObject);
                    }
                    EventBus.getDefault().post(new EventDataReady());
                }

                @Override
                public void onFailure(Call<GetPhysicalExerciseFromAPI.Physical> call, Throwable t) {
                    Log.d("Failure", t.toString());
                }
            });


        } else {
            EventBus.getDefault().postSticky(new EventDataReady());
        }
    }

    @Override
    public void onClick(View v) {
        physicalObject = (PhysicalRealmObject) v.getTag();
        EventBus.getDefault().postSticky( new EventSendPhysicalObject(physicalObject));
        openFragment(new FragmentDoingExcercise());
    }
    private void openFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }
}
