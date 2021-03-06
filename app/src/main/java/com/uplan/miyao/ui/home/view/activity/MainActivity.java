package com.uplan.miyao.ui.home.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.uplan.miyao.R;
import com.uplan.miyao.app.ActivityManager;
import com.uplan.miyao.app.UPLANApplication;
import com.uplan.miyao.base.AppBaseActivity;
import com.uplan.miyao.ui.account.view.fragment.AccountFragment;
import com.uplan.miyao.ui.discover.view.fragment.DiscoverFragment;
import com.uplan.miyao.ui.financial.view.fragment.FinancialFragment;
import com.uplan.miyao.ui.survey.view.fragment.SurveyFragment;
import com.uplan.miyao.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Author：Created by Wbin on 2018/4/09
 *
 * Description：首页
 */

public class MainActivity extends AppBaseActivity {

    private long exitTime = 0;
    //三个按钮布局
    @BindView(R.id.financial_layout)
    RelativeLayout financialLayout;
    @BindView(R.id.survey_layout)
    RelativeLayout surveyLayout;
    @BindView(R.id.discover_layout)
    RelativeLayout discoverLayout;
    @BindView(R.id.account_layout)
    RelativeLayout accountLayout;

    //三个按钮图片及文本
    @BindView(R.id.financial_image)
    ImageView financialImage;
    @BindView(R.id.survey_image)
    ImageView surveyImage;
    @BindView(R.id.discover_image)
    ImageView discoverImage;
    @BindView(R.id.account_image)
    ImageView accountImage;


    private FinancialFragment financialFragment;
    private SurveyFragment surveyFragment;
    private DiscoverFragment discoverFragment;
    private AccountFragment accountFragment;
    FragmentManager fManager;

    /** 选择index: 投资 */
    private static final int SELECT_INDEX_FINANCIAL = 0;

    /** 选择index: 规划宝 */
    private static final int SELECT_INDEX_SURVEY = 1;

    private static final int SELECT_INDEX_DISCOVER = 2;

    /** 选择index: 账户中心页 */
    private static final int SELECT_INDEX_ACCOUNT = 3;

    /** 当前选择index */
    private int mSelectIndex = SELECT_INDEX_FINANCIAL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fManager = getSupportFragmentManager();
        setSelectItem(financialLayout);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        switch (mSelectIndex) {
            case SELECT_INDEX_FINANCIAL:
                if (financialFragment != null) {
                    financialFragment.updateWebData();
                }
                break;
            case SELECT_INDEX_SURVEY:
                if (surveyFragment != null) {
                    surveyFragment.updateWebData();
                }
                break;
            case SELECT_INDEX_DISCOVER:
                if(discoverFragment!=null){
                    discoverFragment.updateWebData();
                }
                break;
            case SELECT_INDEX_ACCOUNT:
                if (accountFragment != null) {
                    accountFragment.updateWebData();
                }
                break;
            default:
                break;
        }
    }

    @OnClick({ R.id.financial_layout, R.id.survey_layout,R.id.discover_layout,R.id.account_layout})
    public void setSelectItem(View view) {

        FragmentTransaction transaction = fManager.beginTransaction();
        switch (view.getId()) {

            case R.id.financial_layout:
                mSelectIndex = SELECT_INDEX_FINANCIAL;
                clearSelect();
                hideFragments(transaction, SELECT_INDEX_FINANCIAL);
                financialImage.setImageResource(R.mipmap.financial_pressed);
                if (financialFragment == null) {
                    financialFragment = new FinancialFragment();
                    transaction.add(R.id.content, financialFragment, "HomeWebViewFragment");

                } else {
                    transaction.show(financialFragment);
                }
                break;
            case R.id.survey_layout:
                mSelectIndex = SELECT_INDEX_SURVEY;
                clearSelect();
                hideFragments(transaction, SELECT_INDEX_SURVEY);
                surveyImage.setImageResource(R.mipmap.survey_pressed);
                if (surveyFragment == null) {

                    surveyFragment = new SurveyFragment();
                    transaction.add(R.id.content, surveyFragment, "FinancialWebViewFragment");

                } else {
                    transaction.show(surveyFragment);
                }
                break;
            case R.id.discover_layout:
                mSelectIndex =SELECT_INDEX_DISCOVER;
                clearSelect();
                hideFragments(transaction,SELECT_INDEX_DISCOVER);
                discoverImage.setImageResource(R.mipmap.discover_pressed);
                if(discoverFragment==null){
                    discoverFragment=new DiscoverFragment();
                    transaction.add(R.id.content,discoverFragment,"discoverFragment");
                }else{
                    transaction.show(discoverFragment);
                }
                break;
            case R.id.account_layout:
                mSelectIndex = SELECT_INDEX_ACCOUNT;
                clearSelect();
                hideFragments(transaction, SELECT_INDEX_ACCOUNT);
                accountImage.setImageResource(R.mipmap.account_pressed);
                if (accountFragment == null) {

                    accountFragment = new AccountFragment();
                    transaction.add(R.id.content, accountFragment, "AccountWebViewFragment");
                } else {
                    transaction.show(accountFragment);
                }
                break;

        }
        transaction.commitAllowingStateLoss();

    }

    /**
     * 清空选择
     */
    public void clearSelect() {
        int bar_item_bg = 0xFFFFFFFF;
        financialImage.setImageResource(R.mipmap.financial_normal);
        financialLayout.setBackgroundColor(bar_item_bg);

        surveyImage.setImageResource(R.mipmap.survey_normal);
        surveyLayout.setBackgroundColor(bar_item_bg);

        discoverImage.setImageResource(R.mipmap.discover_normal);
        discoverLayout.setBackgroundColor(bar_item_bg);
        accountImage.setImageResource(R.mipmap.account_normal);
        accountLayout.setBackgroundColor(bar_item_bg);
    }

    /**
     * 隐藏Fragment
     *
     * @param transaction transaction
     */
    private void hideFragments(FragmentTransaction transaction, int selectIndex) {
        //选中理财页
        if (selectIndex == SELECT_INDEX_FINANCIAL) {
            if (surveyFragment != null) {
                transaction.hide(surveyFragment);
            }
            if(discoverFragment!=null){
                transaction.hide(discoverFragment);
            }
            if (accountFragment != null) {
                transaction.hide(accountFragment);
            }
        }

        //选中规划页
        if (selectIndex == SELECT_INDEX_SURVEY) {
            if (financialFragment != null) {
                transaction.hide(financialFragment);
            }
            if(discoverFragment!=null){
                transaction.hide(discoverFragment);
            }
            if (accountFragment != null) {
                transaction.hide(accountFragment);
            }
        }

        //选中发现页
        if(selectIndex==SELECT_INDEX_DISCOVER){
            if(financialFragment!=null){
                transaction.hide(financialFragment);
            }
            if(surveyFragment!=null){
                transaction.hide(surveyFragment);
            }
            if(accountFragment!=null){
                transaction.hide(accountFragment);
            }

        }

        //选中账户中心页
        if (selectIndex == SELECT_INDEX_ACCOUNT) {
            if (financialFragment != null) {
                transaction.hide(financialFragment);
            }

            if (surveyFragment != null) {
                transaction.hide(surveyFragment);
            }
            if(discoverFragment!=null){
                transaction.hide(discoverFragment);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.show(UPLANApplication.getContext(), "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                ActivityManager.getScreenManager().popAllActivity();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
