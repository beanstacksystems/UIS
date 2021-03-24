package com.bss.uis.ui.navDrawer.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bss.uis.R;
import com.bss.uis.ui.imageSlider.ImageAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        List<String> imageList = new ArrayList<String>();
        imageList.add("iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAYAAACM/rhtAAAABGdBTUEAALGPC/xhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAABmJLR0QA/wD/AP+gvaeTAAAAB3RJTUUH5AQJDhgtBNPzWAAAA7pJREFUWMPt2E2IllUUB/Dfec84muho6KSWiRoGKUUGwRTYJ2K72tWmFrWqiCjctbeVRlSLaBNKRESbiKjAoiLTxKKPhRURmFM2oGR+ZObM2+LemTehZt6voc0cuPBwn3PO/T/nnvs/57nMyZzMybQS/fHSmHSVdWacJs2J/xFg5KT9FbgVI/UZjuIAPsQomprjfYlFG8CigIscEvmYyMMix0WeEXmsjjN17nDVWVxs+rNh00etjJUi94g8L/KQyCdFjohcX8dInfu86uwWuaLYDsw6wKEK7pzIXRXs5Jb/24fsqrp7qu2sR+/xGpWdIhdMu2DRX1B1z1dbswOyOF5T8+rgVOTas1tZbQ5XH20v22gP3JTabdiAV3Csg887Vm02VB9tL92elqjDCP7APmiLOlo6+6rtCNH2iW4TIDQHsBonMdZB9CZlrNqurr76DRAtYm92AbAraR9gxAWlKgxhuIu1hqvtKHGhzwCbNJuU8rVQycX26KKlM1JtD5QN6PcmFLpYK/J7kZ+KHO6AZi4Tub/arp1NHiRyeyXdHSIH2yDqQZFPV5vts0fUrQWXiny1NgQ7RC6fptQtrzpnqs3S2QM3tfAAkXeIHBU5IXKfyIdFbhZ5ZR2bRT5S302I/EXk1sJ/nQFsjy2L0wY24X7cq/R+3+ISrMVp/FYtlmIRfqzzG/ErXsdufIXxdoh+ZoAF3EI8iiewAl/iZbyBQdzp4oZ1FPuxV6ke9+BB3IATeA7P4HRvjWzJoxT5VE3yr0U+VE7lQGu7IolGiJxXRiMufjdA5DKRD9T+8a+am/O6z8loTALcIvJ47Uau6foUtvxdJfJjkb+L3FY/rtvoNRoiX6oN5909U0QL5FaRp+vJHujOZ3G0SuR3lZiX9IUiit+FIveKPDITcc8U2zVK4h8iTvaObkrO4qBy4NZNpzgTwGEswJFSj/vw69jycURhgBW9AJzs2zZiUd+2uHDnpjrTpdOSK5eLfK/SwoutX8eeTvEykc9W2vqo++ahVU/XiXyz/oh/IPKWyo2d+mqIvEnkO9XXuyKv7o0ZWrcIw/XX8ZTIEyKfF3mjyPn/fWMwFbHBWpt3iRwTeVbkC5UhzJRlndTiebgL23GzUmM/wftK6ftZOZ2UHFuF63A7tmAJPsNOvIU/+1OLLwYJl2Ib7qtAl+MCTuFc1ZmPxfWjTih1+TW8jeOd3Hx1fpvTAjof63E9rlU4c6i+O4Wf8A2+wA8FfOdXcn26H0w0/9HsNceFpgno/Y5wTnqRvwFHYciBADUs5AAAACV0RVh0ZGF0ZTpjcmVhdGUAMjAyMC0wNC0wOVQxNDoyNDo0NS0wNDowMLv3ooIAAAAldEVYdGRhdGU6bW9kaWZ5ADIwMjAtMDQtMDlUMTQ6MjQ6NDUtMDQ6MDDKqho+AAAAAElFTkSuQmCC");
        imageList.add("iVBORw0KGgoAAAANSUhEUgAAAIAAAACACAYAAADDPmHLAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEwAACxMBAJqcGAAABTtJREFUeJzt3U9oHGUcxvHv701KNqAlaQ+baw/VTWkDioo99SI2B0t26yGoh1gE9VIKnvRiWvHiwYto9aISED3EkCkoLQpCvIsYaxspghWF5GDVFkFKO6+XBouQdrP7zsw77z6f8867Q59vd/OXgIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIjExcp6orm5ucbVq1ef8N4/5r3f65wbBX733n8LnMmy7Juy7iVCNjMzc9A5d8R7P2Vm4977v733P5rZlzt27Di7uLh4vZAnLuLQ/z9Hp9N5DngdaG71IO/910NDQ8eXlpZWS7inaLTb7Ue892875x7e6jF5nv/qnHt5eXn5Y8CHfP5CA5ienh5pNBoLZjbbzePzPL9uZs9mWfZJkfcVi3a7/bz3/rRzbqjLSz64cuXKCysrKzdC3UO3T7xt09PTI6Ojo4tmdrTba8xsyMyOtlqtn9fW1r4r6t5i0Ol0jpvZaTNz27jsgUajcX+z2cwuX76ch7iPQgLYHB840sPlZmYzKUfQ6XSOA2/1cq2Z7R8ZGblvYmLiTIgIggfQ5/ibko2gn/E3mdn+0dHRvc1ms+8ItvPyc1fz8/Ou0Wh8RH/jbzIz+7Ddbs8FOCsKIca/zVPj4+Pv0ufHcUFfAXbt2nXCzF4KeGQyrwSBxwfAzB7ct2/fTxcvXuz5M6dgnwXMzMyMOed+Ae4NdeZtvPf+WJZlCwWcXbgixt+U5/n62NjYnoWFhX96uT7YW4Bz7mmKGR9q/HZQ5PgAzrmJa9euzfR8fagb8d4fDnXWFmoXQdHjb8rzvOd/+2ABmFkr1Fl3fpp6RFDW+ADe+8lerw35WcA9Ac+6k+gjKHN8ADPr+a03ZAB/BjzrbqKNoOzxAczsj16vDRnA+YBndSO6CKoY/5bve70w5McAn4U6a3tPG0cEFY4P8HmvF4b8NPBTYCPUedtQeQRVjp/n+aXh4eFzvV4f7CuBFy5cuNFqtX4zsydDnbkNlX3FsOL/+TjnnllaWrrU6/VBvxS8trZ2fnJychx4NOS5XSo9gqrH996fXF5efr+fM4J+MwhgamrqJeC90Od2qbS3gwjGfyPLstf6PSf4t4NXVlb87Ozs2Y2NjSbwUOjzu1D4K0Ek479CgB8PK+QHQlKOIKXxocAfCUsxgtTGhwIDgLQiSHF8KDgASCOCVMeHEgKAekeQ8vhQUgBQzwhSHx9KDADqFcEgjA8lBwD1iGBQxocKAoC4Ixik8aGiACDOCAZtfCjx18O3Mj8/71ZXV98BXqzoFrz3/piZ7WTAxocIAoA4IqDCf4uqxocK3wJuF8PbQQXPCVQ7PkQSAEQRQemqHh8iCgAGK4IYxofIAoDBiCCW8SHCACDtCGIaHyINANKMILbxIeIAIK0IYhwfIg8A0ogg1vGhBgFAvSOIeXyoSQBQzwhiHx9qFADUK4I6jA81CwDqEUFdxocaBgBxR1Cn8aGmAUCcEdRtfKhxABBXBHUcH2oeAMQRQV3HhwQCgGojqPP4kEgAUE0EdR8fEgoAyo0ghfEhsQCgnAhSGR8SDACKjSCl8SHRAKCYCFIbHxIOAMJGkOL4kHgAECaCVMeHAQgA/otgfX19p5kd3M613vuTWZa9SoLjQyS/GVSmTqfTAd4E9tzpcXmer5nZiSzLvijnzqoxcAEAHDp0aHj37t2P37x587BzrsWtv3SS5/lfZvaDc+7cgQMHvjp16lSQv80nIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiLSv38BYeOka7164mgAAAAASUVORK5CYII=");
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ViewPager mViewPager = (ViewPager)root.findViewById(R.id.imgviewPagerMain);
        ImageAdapter adapterView = new ImageAdapter(getContext(),imageList);
        mViewPager.setAdapter(adapterView);
//        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return root;
    }
    private void initializeViewComponents()
    {

    }
}