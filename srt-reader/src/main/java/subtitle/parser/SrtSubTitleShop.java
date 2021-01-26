package subtitle.parser;


import subtitle.model.TuziSubTitleInfoTreeMap;
import subtitle.parser.srt.SRTReader;

import java.io.File;
import java.io.InputStream;

/**
 * 加载srt字幕的车间
 * 
 * @author Vermouth
 * 
 */
public class SrtSubTitleShop implements ISubTitleParser {
	@Override
	public TuziSubTitleInfoTreeMap parser(InputStream is) {
		TuziSubTitleInfoTreeMap mTuziSubTitleInfoTreeMap = SRTReader.read(is);
		return mTuziSubTitleInfoTreeMap;
	}

	@Override
	public TuziSubTitleInfoTreeMap parser(File file) {
		TuziSubTitleInfoTreeMap mTuziSubTitleInfoTreeMap = SRTReader.read(file);
		return mTuziSubTitleInfoTreeMap;
	}

}
