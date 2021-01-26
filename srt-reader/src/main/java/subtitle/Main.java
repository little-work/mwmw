package subtitle;

/**
 *
 * @email mail@wangdingchun.net
 * @author Vermouth
 * @version 1.0
 * @created 2015-2-27 下午3:16:22
 * @changeRecord [修改记录]<br />
 */
public class Main {

	public static void main(String[] args) {
		PlayerSubTitleProxy mPlayerSubTitleProxy = new PlayerSubTitleProxy();
		mPlayerSubTitleProxy.loaderSubTitle("E:\\字幕文件\\srt\\restlt_v1.1.srt");
	}
}
