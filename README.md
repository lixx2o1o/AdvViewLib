# AdvViewLib

//Java使用方法：

    //ViewPager
    mViewPager = (ViewPager)this.findViewById(R.id.viewpager);
    //下方跟随的圆点
    pointGroup = (LinearLayout) findViewById(R.id.pointgroup);

    mViewPagerHelper = new ViewPagerHelper(this,mViewPager,pointGroup);
    //切换动画 引用DepthPageTransformer/ZoomOutPageTransformer
    mViewPagerHelper.setPageTransformer(new DepthPageTransformer());
    //切换速度
    mViewPagerHelper.setViewPagerScrollSpeed(2000);
    //item点击事件
    mViewPagerHelper.setOnItemClickListener(new ViewPagerHelper.OnItemClickListener() {
        @Override
        public void onClick(int position) {
            Toast.makeText(MainActivity.this,"position--->"+position,Toast.LENGTH_SHORT).show();
        }
    });
//xml布局

    <android.support.v4.view.ViewPager
        android:id="@+id/viewpager"
        android:layout_width="match_parent"
        android:layout_height="150dp"/>

    <LinearLayout
        android:id="@+id/pointgroup"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="3dp"
        android:orientation="horizontal">
    </LinearLayout>
</RelativeLayout>
