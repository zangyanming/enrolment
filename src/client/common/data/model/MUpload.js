define(['knockout','mAjax'], function (ko,MAjax) {
    function Upload(params) {
        this.fields=[
            {name: 'upload', type: 'file', alias: '文件'}
        ];

        this.form = 'UploadForm';
        this.callParent(params);
    }

    Upload.extend(MAjax);

    return Upload;
});
