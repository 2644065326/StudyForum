package com.hbsf.detail.posts;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbsf.arouter_annotation.ARouter;
import com.hbsf.base.view.BaseActivity;
import com.hbsf.detail.R;
import com.hbsf.detail.photoengine.GlideEngine;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ARouter(path = "/detail/IssueActivity")
public class IssueActivity extends BaseActivity {
    private String url = "https://www.baidu.com/link?url=0Z7v-Q-RL7FwvtepDStMmgnmf1gzTUpBWuictUOSNAnilrO5vNi7r4uM3K8OBECi2A02GxXsH7qfPl39CrGnAlkOcSYi6RSUsLxybpNXQblwHkykpY_8LtEIhFd1FeM7WM9NBiz7JOlTzi3mUrTjPudAVC-16Z-0zaDvtezTvHWZPhuGdI9NgmdAKShoS_OdPfpfKPNJMcND-gCTfh6cDWvCsjFtjzE-HgphDsQDUySJQTofqEF_DVq7NtpBahB19_hPutJNSL4C6Ib1SEBDBciGIiu5UATD3oI1Lb0nxnZ1tUOzX--v34bOUnoj_eCHZ4MxquA6Zo8bQzO1O370B5weANOIRYNEcbWeYllHMlTBBktmSyVvOIaQXPG665QzFCu-qegq-W5pUCEy8bhfez9_-0Mnz15-XXVjhaGBRA3g7iwzts4ICvcKz-jQHC8twLYLK10ovWQkdH7TyPUYvpaZPXUK_B0dHaY68hxneJxx34aDqJHakbbTjqHThAB9bcrzxxfZ-vxDLxKoeSi73t6oSB2Y9hYqumB5R0K9jfgI22uGoh9NiemZwEelszJHeGYbAS4jwAOL3KKYa8SP_xp8IaJNDYO4IUaUy8pMNus5oNmBNQfQOJ2RJISp6w-ZZngsGf4_MTtNrBfUJkJV03EQlcHOBY1Kn2w6Q_lNilt-rd3f0Xh3JzpbeciGnwXExTToqLpcJS3CgWaoOGA9PRQwo9jvW0RW7X2brhPoRsWDFv2Cgi6c6NBtGyU1DbiBrv0Z2RsaoKrVPcyg1w851l7pKfszUaoEfwrQWog1wUAzy-WEvIFYW6SMS-mB42oA&wd=&eqid=c56ad06f000103ff00000002622d63b4";
    private ImageView backBtn;
    private TextView issueBtn;
    private Context context;
    @Override
    public int getLayoutId() {
        return R.layout.activity_issue;
    }

    @Override
    public void initView() {
        context = this;
        backBtn = findViewById(R.id.back_btn);
        issueBtn = findViewById(R.id.issue_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        issueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(context)
                .openGallery(SelectMimeType.ofImage())
                .setImageEngine(GlideEngine.createGlideEngine())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {
                        ArrayList<String> pics = new ArrayList<>();
                        for (LocalMedia lm : result) {
                            pics.add(lm.getPath());
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });




    }


}
