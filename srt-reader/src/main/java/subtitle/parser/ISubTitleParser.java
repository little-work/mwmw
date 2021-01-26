package subtitle.parser;

import subtitle.model.TuziSubTitleInfoTreeMap;

import java.io.File;
import java.io.InputStream;

public interface ISubTitleParser {
	public TuziSubTitleInfoTreeMap parser(InputStream is);
	public TuziSubTitleInfoTreeMap parser(File file);
}
