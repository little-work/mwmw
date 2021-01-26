package subtitle.parser;


import subtitle.model.TuziSubTitleInfoTreeMap;

import java.io.File;
import java.io.InputStream;

public class SubTitleParserFactory {
	private ISubTitleParser mSubTitleParser;

	public TuziSubTitleInfoTreeMap parserSubTitle(String type, File file) {
		if ("srt".equals(type)) {
			mSubTitleParser = new SrtSubTitleShop();
			return mSubTitleParser.parser(file);
		} else {
			return null;
		}
	}

	public TuziSubTitleInfoTreeMap parserSubTitle(String type, InputStream is) {
		if ("srt".equals(type)) {
			mSubTitleParser = new SrtSubTitleShop();
			return mSubTitleParser.parser(is);
		} else {
			return null;
		}

	}
}
