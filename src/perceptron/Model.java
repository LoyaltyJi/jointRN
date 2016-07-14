package perceptron;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import perceptron.Feature.featureName;

public class Model {
	public HashMap<String, Set<String>> m_posCloseSet = new HashMap<String, Set<String>>();
	public HashMap<String, Map<String, Integer>> m_wordPOSSets = new HashMap<String, Map<String, Integer>>();
	public HashMap<String, Integer> m_wordFreq = new HashMap<String, Integer>();
	public HashMap<String, Map<String, Integer>> m_startCharPOSSets = new HashMap<String, Map<String, Integer>>();
	public HashMap<String, Integer> m_startCharFreq = new HashMap<String, Integer>();

	// feature templates abstd::cout characters
	public HashMap<String, Feature> m_mapOrgCharUnigram = new HashMap<String, Feature>(); // C0
	public HashMap<String, Feature> m_mapOrgCharBigram = new HashMap<String, Feature>(); // C-1C0
	public HashMap<String, Feature> m_mapOrgCharTrigram = new HashMap<String, Feature>(); // C-2C-1C0

	// feature templates abstd::cout words
	public HashMap<String, Feature> m_mapOrgSeenWords = new HashMap<String, Feature>(); // w_1
	public HashMap<String, Feature> m_mapOrgLastWordByWord = new HashMap<String, Feature>(); // w-2w-1
	public HashMap<String, Feature> m_mapOrgCurrentWordLastChar = new HashMap<String, Feature>();// w_1_end_w_2
	public HashMap<String, Feature> m_mapOrgLastWordFirstChar = new HashMap<String, Feature>();// w_1_c_0
	public HashMap<String, Feature> m_mapOrgFirstCharLastWordByWord = new HashMap<String, Feature>();// start_w_1_C_0
	public HashMap<String, Feature> m_mapOrgLastWordByLastChar = new HashMap<String, Feature>();// w_1_c_0_t_1
	public HashMap<String, Feature> m_mapOrgSeparateChars = new HashMap<String, Feature>();// end_w_1_c_0
	public HashMap<String, Feature> m_mapOrgConsecutiveWords = new HashMap<String, Feature>();// char_bigram
																								// for
																								// app
	public HashMap<String, Feature> m_mapOrgFirstAndLastChars = new HashMap<String, Feature>(); // start_w_1end_w_1
	public HashMap<String, Feature> m_mapOrgOneCharWord = new HashMap<String, Feature>();// w-1
																							// if(len_w-1==1)
	public HashMap<String, Feature> m_mapOrgLengthByFirstChar = new HashMap<String, Feature>();// start_w_1_len_w_1
	public HashMap<String, Feature> m_mapOrgLengthByLastChar = new HashMap<String, Feature>();// end_w_1_len_w_1
	public HashMap<String, Feature> m_mapOrgLengthByLastWord = new HashMap<String, Feature>();// w_2_len_w_1
	public HashMap<String, Feature> m_mapOrgLastLengthByWord = new HashMap<String, Feature>();// w_1_len_w_2

	// feature templates tag
	public HashMap<String, Feature> m_mapOrgCurrentTag = new HashMap<String, Feature>(); // w_1_t_1
	public HashMap<String, Feature> m_mapOrgLastTagByTag = new HashMap<String, Feature>(); // t-1t0
	public HashMap<String, Feature> m_mapOrgLastTwoTagsByTag = new HashMap<String, Feature>(); // t-2t-1t0
	public HashMap<String, Feature> m_mapOrgTagByLastWord = new HashMap<String, Feature>(); // w-1t0
	public HashMap<String, Feature> m_mapOrgLastTagByWord = new HashMap<String, Feature>(); // w-1t-2
	public HashMap<String, Feature> m_mapOrgTagByFirstChar = new HashMap<String, Feature>();// first_char_0,
																							// tag_0
	public HashMap<String, Feature> m_mapOrgTagByLastChar = new HashMap<String, Feature>();// end_w_1_t_1
	public HashMap<String, Feature> m_mapOrgTagByChar = new HashMap<String, Feature>();// (first_char_0,
																						// tag_0
																						// for
																						// two
																						// action
	public HashMap<String, Feature> m_mapOrgTagOfOneCharWord = new HashMap<String, Feature>();// end_w_2_w_1_c_0
																								// if
																								// len_w_1=1
	public HashMap<String, Feature> m_mapOrgRepeatedCharByTag = new HashMap<String, Feature>();
	public HashMap<String, Feature> m_mapOrgTagByWordAndPrevChar = new HashMap<String, Feature>();// w_1_end_w_2_t_1
	public HashMap<String, Feature> m_mapOrgTagByWordAndNextChar = new HashMap<String, Feature>();
	public HashMap<String, Feature> m_mapOrgTaggedCharByFirstChar = new HashMap<String, Feature>();// first_char
																									// char_unigram,
																									// tag_0
																									// for
																									// app
	public HashMap<String,Feature> m_mapOrgTaggedCharByFirstTaggedChar = new HashMap<String,Feature>(); //c_0t_0c_1t_1 for app
	public HashMap<String, Feature> m_mapOrgTaggedCharByLastChar = new HashMap<String, Feature>();// w_1��char��last_char

	// extra features
	public HashMap<String, Feature> m_mapOrgTaggedSeparateChars = new HashMap<String, Feature>();// last_char_1,
																									// tag_1,
																									// first_char_0,
																									// tag_0
	public HashMap<String, Feature> m_mapOrgTaggedConsecutiveChars = new HashMap<String, Feature>();// char_bigram,
																									// tag_0
																									// for
																									// app

	public HashMap<String, Feature> m_mapOrgWordTagTag = new HashMap<String, Feature>();// word_2,
																						// tag_0_tag_1
	public HashMap<String, Feature> m_mapOrgTagWordTag = new HashMap<String, Feature>();// word_1,
																						// tag_0_tag_2
	public HashMap<String, Feature> m_mapOrgFirstCharBy2Tags = new HashMap<String, Feature>();// first_char_0,
																								// tag_0_tag_1
	public HashMap<String, Feature> m_mapOrgFirstCharBy3Tags = new HashMap<String, Feature>();// first_char_0,
																								// tag_0_tag_1_tag_2
	public HashMap<String, Feature> m_mapOrgFirstCharAndChar = new HashMap<String, Feature>();

	public HashMap<String, Feature> m_mapOrgSepCharAndNextChar = new HashMap<String, Feature>();
	public HashMap<String, Feature> m_mapOrgAppCharAndNextChar = new HashMap<String, Feature>();

	public HashMap<String, Feature> m_mapOrgPartialWord = new HashMap<String, Feature>();
	public HashMap<String, Feature> m_mapOrgPartialLengthByFirstChar = new HashMap<String, Feature>();
	public HashMap<String, Feature> m_mapOrgLengthByTagAndFirstChar = new HashMap<String, Feature>();
	public HashMap<String, Feature> m_mapOrgLengthByTagAndLastChar = new HashMap<String, Feature>();

	public HashMap<String, Feature> m_mapOrgTag0Tag1Size1 = new HashMap<String, Feature>();// t_1_t_0_len(w_1)
	public HashMap<String, Feature> m_mapOrgTag1Tag2Size1 = new HashMap<String, Feature>();// t_2_t_1_len(w_1)
	public HashMap<String, Feature> m_mapOrgTag0Tag1Tag2Size1 = new HashMap<String, Feature>();// t_1_t_0_len(w_1)
    // statistical information
	public HashMap<String, Integer> m_mapWordFrequency = new HashMap<String, Integer>();
	public HashMap<String, String> m_mapTagDictionary = new HashMap<String, String>();
	public HashMap<String, String> m_mapCharTagDictionary = new HashMap<String, String>();
	public HashMap<String, String> m_mapCanStart = new HashMap<String, String>();
	// normalize features
	public HashMap<String, Feature> m_mapNorCharUnigram = new HashMap<String, Feature>(); // C0
	public HashMap<String, Feature> m_mapNorCharBigram = new HashMap<String, Feature>(); // C-1C0
	public HashMap<String, Feature> m_mapNorCharTrigram = new HashMap<String, Feature>(); // C-2C-1C0

	// feature templates abstd::cout words
	public HashMap<String, Feature> m_mapNorSeenWords = new HashMap<String, Feature>(); // w_1
	public HashMap<String, Feature> m_mapNorLastWordByWord = new HashMap<String, Feature>(); // w-2w-1
	public HashMap<String, Feature> m_mapNorCurrentWordLastChar = new HashMap<String, Feature>();// w_1_end_w_2
	public HashMap<String, Feature> m_mapNorLastWordFirstChar = new HashMap<String, Feature>();// w_1_c_0
	public HashMap<String, Feature> m_mapNorFirstCharLastWordByWord = new HashMap<String, Feature>();// start_w_1_C_0
	public HashMap<String, Feature> m_mapNorLastWordByLastChar = new HashMap<String, Feature>();// w_1_c_0_t_1
	public HashMap<String, Feature> m_mapNorSeparateChars = new HashMap<String, Feature>();// end_w_1_c_0
	public HashMap<String, Feature> m_mapNorConsecutiveChars = new HashMap<String, Feature>();// char_bigram
																								// for
																								// app
	public HashMap<String, Feature> m_mapNorFirstAndLastChars = new HashMap<String, Feature>(); // start_w_1end_w_1
	public HashMap<String, Feature> m_mapNorOneCharWord = new HashMap<String, Feature>();// w-1
																							// if(len_w-1==1)
	public HashMap<String, Feature> m_mapNorLengthByFirstChar = new HashMap<String, Feature>();// start_w_1_len_w_1
	public HashMap<String, Feature> m_mapNorLengthByLastChar = new HashMap<String, Feature>();// end_w_1_len_w_1
	public HashMap<String, Feature> m_mapNorLengthByLastWord = new HashMap<String, Feature>();// w_2_len_w_1
	public HashMap<String, Feature> m_mapNorLastLengthByWord = new HashMap<String, Feature>();// w_1_len_w_2

	// feature templates tag
	public HashMap<String, Feature> m_mapNorCurrentTag = new HashMap<String, Feature>(); // w_1_t_1
	public HashMap<String, Feature> m_mapNorLastTagByTag = new HashMap<String, Feature>(); // t-1t0
	public HashMap<String, Feature> m_mapNorLastTwoTagsByTag = new HashMap<String, Feature>(); // t-2t-1t0
	public HashMap<String, Feature> m_mapNorTagByLastWord = new HashMap<String, Feature>(); // w-1t0
	public HashMap<String, Feature> m_mapNorLastTagByWord = new HashMap<String, Feature>(); // w-1t-2
	public HashMap<String, Feature> m_mapNorTagByFirstChar = new HashMap<String, Feature>();// first_char_0,
																							// tag_0
	public HashMap<String, Feature> m_mapNorTagByLastChar = new HashMap<String, Feature>();// end_w_1_t_1
	public HashMap<String, Feature> m_mapNorTagByChar = new HashMap<String, Feature>();// (first_char_0,						// tag_0
																						// for
																						// two
																						// action
	// disease feature , tokenPos
	public HashMap<String, Feature> m_mapOrgIsDisease = new HashMap<String, Feature>();// (first_char_0,
	public HashMap<String, Feature> m_mapOrgIsPartialDisease = new HashMap<String,Feature>();
	public HashMap<String, Feature> m_mapOrgPOSConsecutiveChars = new HashMap<String, Feature>();
	public HashMap<String, Feature>  m_mapOrgPreWordPos =new HashMap<String,Feature>();
	
	public HashMap<String, Feature> m_mapNorTagOfOneCharWord = new HashMap<String, Feature>();// end_w_2_w_1_c_0
																								// if
																								// len_w_1=1
	public HashMap<String, Feature> m_mapNorRepeatedCharByTag = new HashMap<String, Feature>();
	public HashMap<String, Feature> m_mapNorTagByWordAndPrevChar = new HashMap<String, Feature>();// w_1_end_w_2_t_1
	public HashMap<String, Feature> m_mapNorTagByWordAndNextChar = new HashMap<String, Feature>();
	public HashMap<String, Feature> m_mapNorTaggedCharByFirstChar = new HashMap<String, Feature>();// first_char
																									// char_unigram,
																									// tag_0
																									// for
																									// app
	public HashMap<String, Feature> m_mapNorTaggedCharByLastChar = new HashMap<String, Feature>();// w_1��char��last_char

	// extra features
	public HashMap<String, Feature> m_mapNorTaggedSeparateChars = new HashMap<String, Feature>();// last_char_1,
																									// tag_1,
																									// first_char_0,
																									// tag_0
	public HashMap<String, Feature> m_mapNorTaggedConsecutiveChars = new HashMap<String, Feature>();// char_bigram,
																									// tag_0
																									// for
																									// app

	public HashMap<String, Feature> m_mapNorWordTagTag = new HashMap<String, Feature>();// word_2,
																						// tag_0_tag_1
	public HashMap<String, Feature> m_mapNorTagWordTag = new HashMap<String, Feature>();// word_1,
																						// tag_0_tag_2
	public HashMap<String, Feature> m_mapNorFirstCharBy2Tags = new HashMap<String, Feature>();// first_char_0,
																								// tag_0_tag_1
	public HashMap<String, Feature> m_mapNorFirstCharBy3Tags = new HashMap<String, Feature>();// first_char_0,
																								// tag_0_tag_1_tag_2
	public HashMap<String, Feature> m_mapNorFirstCharAndChar = new HashMap<String, Feature>();

	public HashMap<String, Feature> m_mapNorSepCharAndNextChar = new HashMap<String, Feature>();
	public HashMap<String, Feature> m_mapNorAppCharAndNextChar = new HashMap<String, Feature>();

	public HashMap<String, Feature> m_mapNorPartialWord = new HashMap<String, Feature>();
	public HashMap<String, Feature> m_mapNorPartialLengthByFirstChar = new HashMap<String, Feature>();
	public HashMap<String, Feature> m_mapNorLengthByTagAndFirstChar = new HashMap<String, Feature>();
	public HashMap<String, Feature> m_mapNorLengthByTagAndLastChar = new HashMap<String, Feature>();

	public HashMap<String, Feature> m_mapNorTag0Tag1Size1 = new HashMap<String, Feature>();// t_1_t_0_len(w_1)
	public HashMap<String, Feature> m_mapNorTag1Tag2Size1 = new HashMap<String, Feature>();// t_2_t_1_len(w_1)
	public HashMap<String, Feature> m_mapNorTag0Tag1Tag2Size1 = new HashMap<String, Feature>();// t_1_t_0_len(w_1)

	// feature templates knowledge
	public HashMap<String, Feature> m_mapNorTagByFirstCharCat = new HashMap<String, Feature>();// first_char_cat_0,
																								// tag_0
	public HashMap<String, Feature> m_mapNorTagByLastCharCat = new HashMap<String, Feature>();// last_char_cat_1,
																								// tag_1
	public HashMap<String, Feature> m_mapNorSeparateCharCat = new HashMap<String, Feature>();
	public HashMap<String, Feature> m_mapNorConsecutiveCharCat = new HashMap<String, Feature>();
	public HashMap<String, Feature> m_mapNorConsecutiveCharTagCat = new HashMap<String, Feature>();
	public HashMap<String, Feature> m_mapNorSeparateWordCat = new HashMap<String, Feature>();
	public HashMap<String, Feature> m_mapNorTagByCurWordCat = new HashMap<String, Feature>();

	// ���� feature templates
	public HashMap<String, Feature> m_mapWordSense = new HashMap<String, Feature>(); // w1s1
	public HashMap<String, Feature> m_mapLastWordAndWordSense = new HashMap<String, Feature>(); // w1s1w0
	public HashMap<String, Feature> m_mapPreWordAndWordSense = new HashMap<String, Feature>(); // w1s1w2
	public HashMap<String, Feature> m_mapStartPreAndWordSense = new HashMap<String, Feature>(); // w1s1start_w2
																								// del
	public HashMap<String, Feature> m_mapEndPreAndWordSense = new HashMap<String, Feature>(); // w1s1end_w2
	public HashMap<String, Feature> m_mapStartLastAndWordSense = new HashMap<String, Feature>(); // w1s1start_w0
																									// del
	public HashMap<String, Feature> m_mapLastCharAndWordSense = new HashMap<String, Feature>(); // w1s1C0
	public HashMap<String, Feature> m_mapTagWordSense = new HashMap<String, Feature>(); // w1s1t1
	public HashMap<String, Feature> m_mapPreTagAndWordSense = new HashMap<String, Feature>(); // w1s1t2
	public HashMap<String, Feature> m_mapLastTagAndWordSense = new HashMap<String, Feature>(); // w1s1t0
	public HashMap<String, Feature> m_mapThreeWordAndSense = new HashMap<String, Feature>(); // w1s1w0w2
	public HashMap<String, Feature> m_mapPreLastTagAndWordSense = new HashMap<String, Feature>(); // w1s1t0t2
	public HashMap<String, Feature> m_mapTwoWordSense = new HashMap<String, Feature>(); // w1s1w2s2
	public HashMap<String, Feature> m_mapLastTagAndTwoWordSense = new HashMap<String, Feature>(); // w1s1w2s2t0
	public HashMap<String, Feature> m_mapLastWordAndTwoWordSense = new HashMap<String, Feature>(); // w1s1w2s2w0
	public HashMap<String, Feature> m_mapLastTagAndPreWordSense = new HashMap<String, Feature>(); // w2s2t0
	public HashMap<String, Feature> m_mapLastWordAndPreWordSense = new HashMap<String, Feature>(); // w2s2w0
	public HashMap<String, Feature> m_mapLastCharAndPreWordSense = new HashMap<String, Feature>(); // w3s2c0

	// ����ģ��
	public HashMap<String, Feature> m_mapGram2 = new HashMap<String, Feature>();//
	public HashMap<String, Feature> m_mapGram3 = new HashMap<String, Feature>();//
	public HashMap<String, Feature> m_mapGram4 = new HashMap<String, Feature>();//
	
	//POS
	public HashMap<String, Feature> m_mapOrgTagCharPOS = new HashMap<String, Feature>(); //t_0+ c_0+ p_0
	public HashMap<String, Feature> m_mapOrgTagPOS = new HashMap<String, Feature>();//t_0 + p_0 
	public HashMap<String, Feature> m_mapOrgCharPOSTagBigram = new HashMap<String, Feature>();//t_0 + c_0 + p_0 + p_1
	public HashMap<String, Feature> m_mapOrgCharPOSTagTrigram = new HashMap<String, Feature>(); //t_0 + c_0 + p_0 +p_1 + p_2
	public HashMap<String, Feature> m_mapOrgTagsPOSBigram = new HashMap<String, Feature>(); //t_0 + t_1 + p_1 + p_0
	public HashMap<String, Feature> m_mapOrgTagsPOSTrigram = new HashMap<String, Feature>(); // t_0 + t_1 + t_2 + p_0 + p_1 + p_2
	public HashMap<String, Feature> m_mapOrgTagPOSBigram = new HashMap<String, Feature>(); //t_0 + p_1 + p_0
	public HashMap<String, Feature> m_mapOrgTagPOSTrigram = new HashMap<String, Feature>(); // t_0  + p_0 + p_1 + p_2
	
	public HashMap<String, Feature> m_mapOrgCharsAndPOSAndTags = new HashMap<String, Feature>();//c_1 + c_0 + p_1 + p_0 + t_1 +t_0
	public HashMap<String, Feature> m_mapOrgConsecutivePOSAndConsecutiveTags = new HashMap<String, Feature>();//p_1 + p_0 +t_1 + t_0
	public HashMap<String, Feature> m_mapOrgConsecutiveCharsAndConsecutivePOS = new HashMap<String, Feature>();//c_1 + c_0 +p_1+p_0
	public HashMap<String, Feature> m_mapOrgPOSCharByFirstChar = new HashMap<String, Feature>(); //c_0 + p_0 + c_1
	public HashMap<String, Feature> m_mapOrgConsecutiveCharsAndPOS = new HashMap<String, Feature>(); //c_1+p_1+c_0+p_0
	
	public HashMap<String, Feature> m_mapOrgCharPrefix = new HashMap<String, Feature>();//prefix,suffix
	public HashMap<String, Feature> m_mapOrgCharSuffix = new HashMap<String, Feature>();
	
	public HashMap<String, Feature> m_mapOrgLastCharBrown = new HashMap<String, Feature>();//brown cluster
	public HashMap<String, Feature> m_mapOrgLastCharVector = new HashMap<String, Feature>();

	public HashMap<String, Feature> m_mapOrgIsAbbre = new HashMap<String,Feature>();	//abbreviation
	
	public HashMap<String, Feature> m_mapOrgStemBigram = new HashMap<String,Feature>();//stem s_2+s_1
	public HashMap<String, Feature> m_mapOrgStemLabel = new HashMap<String,Feature>();//stem s_0+l_0

	public HashMap<String, Feature> m_mapOrgWordAfterWord = new HashMap<String,Feature>(); 	//bag of words  //w0w1
	public HashMap<String, Feature> m_mapOrgBiWordAfterWord = new HashMap<String, Feature>();//w0w1w2
	public HashMap<String, Feature> m_mapOrgBeforeWordAndAfterWord = new HashMap<String, Feature>();//w-1w0w1
	
	public HashMap<String, Feature> m_mapOrgWordLabelAfterWord = new HashMap<String, Feature>();//w_0+l_0+w1
	//add w1
	public HashMap<String, Feature> m_mapNorSeenWordsAfterWord = new HashMap<String, Feature>();//  n_1 + w1;	
	public HashMap<String, Feature> m_mapNorLastWordByWordAfterWord = new HashMap<String, Feature>();// n_1 + "_" + n_2 + w1;
	public HashMap<String, Feature> m_mapNorFirstAndLastCharsAfterWord = new HashMap<String, Feature>();// start_n_1 + end_n_1 + w1;
	public HashMap<String, Feature> m_mapNorLengthByLastCharAfterWord = new HashMap<String, Feature>();// end_n_1 + len_n_1 + w1;
	public HashMap<String, Feature> m_mapNorLengthByFirstCharAfterWord = new HashMap<String, Feature>();// start_n_1 + len_n_1 + w1;
	public HashMap<String, Feature> m_mapNorCurrentWordLastCharAfterWord = new HashMap<String, Feature>();// end_n_2 + "_" + n_1 + w1;
	public HashMap<String, Feature> m_mapNorLastWordByLastCharAfterWord = new HashMap<String, Feature>();// end_n_2 + end_n_1 + w1;
	public HashMap<String, Feature> m_mapNorLengthByLastWordAfterWord = new HashMap<String, Feature>();//n_2 + len_n_1 + w1;
	public HashMap<String, Feature> m_mapNorLastLengthByWordAfterWord = new HashMap<String, Feature>();//len_n_2 + n_1 + w1;
	public HashMap<String, Feature> m_mapNorCurrentTagAfterWord = new HashMap<String, Feature>();//n_1 + l_1 + w1;
	public HashMap<String, Feature> m_mapNorTagByWordAndPrevCharAfterWord = new HashMap<String, Feature>();//n_1 + l_1 + end_n_2 + w1;
	public HashMap<String, Feature> m_mapNorTagByLastWordAfterWord = new HashMap<String, Feature>();//n_1 + l_0 + w1;
	public HashMap<String, Feature> m_mapNorLastTagByWordAfterWord = new HashMap<String, Feature>();//n_1 + l_2 + w1;
	public HashMap<String, Feature> m_mapNorTagByLastCharAfterWord = new HashMap<String, Feature>();//end_n_1 + l_1 + w1;
	public HashMap<String, Feature> m_mapNorLastTagAfterWord = new HashMap<String, Feature>();//l_1 + l_0 + w1;
	public HashMap<String, Feature> m_mapNorTag0Tag1Size1AfterWord = new HashMap<String, Feature>();//l_1 + l_0 + len_n_1 + w1;
	public HashMap<String, Feature> m_mapNorTag1Tag2Size1AfterWord = new HashMap<String, Feature>();//l_2 + l_1 + len_n_1 + w1;
	public HashMap<String, Feature> m_mapNorTag0Tag1Tag2Size1AfterWord = new HashMap<String, Feature>();//l_2 + l_1 + l_0 + len_n_1 + w1;
	public HashMap<String, Feature> m_mapNorLastTwoTagsByTagAfterWord = new HashMap<String, Feature>();//l_2 + l_1 + l_0 + w1;
	public HashMap<String, Feature> m_mapNorTagByCharAfterWord = new HashMap<String, Feature>();//l_0 + w_0 + w1;
	public HashMap<String, Feature> m_mapNorFirstCharBy2TagsAfterWord = new HashMap<String, Feature>();//l_0 + l_1 + w_0 + w1;
	public HashMap<String, Feature> m_mapNorSeparateCharsAfterWord = new HashMap<String, Feature>();//w_1 + w_0 + w1;
	public HashMap<String, Feature> m_mapNorLastWordFirstCharAfterWord = new HashMap<String, Feature>();//n_1 + "_" + w_0 + w1;
	public HashMap<String, Feature> m_mapNorFirstCharLastWordByWordAfterWord = new HashMap<String, Feature>();//start_n_1 + w_0 + w1;
	public HashMap<String, Feature> m_mapNorTagWordTagAfterWord = new HashMap<String, Feature>();//l_2 + n_1 + l_0 + w1;
	public HashMap<String, Feature> m_mapNorWordTagTagAfterWord = new HashMap<String, Feature>();//n_2 + l_1 + l_0 + w1;
	public HashMap<String, Feature> m_mapNorTagByFirstCharAfterWord = new HashMap<String, Feature>();//start_n_1 + l_1 + w1;
	public HashMap<String, Feature> m_mapNorFirstAndLastCharsAndLabelAfterWord = new HashMap<String, Feature>();//start_n_1 + end_n_1 + l_1 + w1;
	//add w1 w2
	public HashMap<String, Feature> m_mapNorSeenWordsAfterWords = new HashMap<String, Feature>();// + n_1 + w1 + w2
	public HashMap<String, Feature> m_mapNorCurrentTagAfterWords = new HashMap<String, Feature>();//+ n_1 +l_1+ w1 +w2
	public HashMap<String, Feature> m_mapNorFirstAndLastCharsAndLabelAfterWords = new HashMap<String, Feature>();// + start_n_1 + end_n_1 + l_1 + w1 +w2
	public HashMap<String, Feature> m_mapNorFirstAndLastCharsAfterWords = new HashMap<String, Feature>();// + start_n_1 + end_n_1 + w1 + w2
	public HashMap<String, Feature> m_mapNorLengthByLastCharAfterWords = new HashMap<String, Feature>();// + end_n_1 + len_n_1 + w1 + w2
	public HashMap<String, Feature> m_mapNorLastWordByLastCharAfterWords = new HashMap<String, Feature>();//+ end_n_2 + end_n_1 + w1 + w2
	public HashMap<String, Feature> m_mapNorTagByLastCharAfterWords = new HashMap<String, Feature>();//+ end_n_1 + l_1 + w1 + w2
	public HashMap<String, Feature> m_mapNorLastTagAfterWords = new HashMap<String, Feature>(); //l_1 + w1 + w2
	
	public Model() {
		// loadPosCloseSet();
	}

	public void init(String filename, boolean bNewTrain) {
		if (bNewTrain == true) {
			m_posCloseSet = new HashMap<String, Set<String>>();
			m_posCloseSet.put("Y", new HashSet<String>());//Y对应的实体的集合，不重复
			m_posCloseSet.put("N", new HashSet<String>());	//N对应的实体的集合，不重复{Y=[],N=[]}		
			m_wordPOSSets = new HashMap<String, Map<String, Integer>>();//预料中每个实体，实体对应的label, 这个实体的label次数
			m_wordFreq = new HashMap<String, Integer>();//实体，实体出现的次数
			m_startCharPOSSets = new HashMap<String, Map<String, Integer>>();//实体的第一个单词，第一个单词对应的词性，和单词词性出现的次数
			m_startCharFreq = new HashMap<String, Integer>();//第一个单词出现的次数
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename), "UTF-8"));
			String line = "";
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty())
					continue;
				String[] temstrs = line.trim().split("\\s+");
				for (String tempStr : temstrs) {
					int index = tempStr.indexOf("_");
					if (index == -1) {
						System.out.println("error input line: " + line);
						continue;
					}
					String[] theWordSense = tempStr.substring(0, index).split("\\|");
					String theWord = theWordSense[0];
					String theSense = "";
					if (theWordSense.length == 2) {
						theSense = theWordSense[1];
					}
					// System.out.println(theWord + "   " + line);
					String theFirstChar;
					if(theWord.indexOf("#") != -1){
						theFirstChar = theWord.substring(0, theWord.indexOf("#"));
					}else{
						theFirstChar = theWord;
					}
					
//					String theFirstChar = theWord.substring(0, 1);
					String theTag = tempStr.substring(index + 1,tempStr.length());

					if (!m_wordFreq.containsKey(theWord)) {
						m_wordFreq.put(theWord, 0);
						m_wordPOSSets.put(theWord, new HashMap<String, Integer>());
					}
					m_wordFreq.put(theWord, m_wordFreq.get(theWord) + 1);

					if (!m_wordPOSSets.get(theWord).containsKey(theTag)) {
						m_wordPOSSets.get(theWord).put(theTag, 0);
					}
					m_wordPOSSets.get(theWord).put(theTag,m_wordPOSSets.get(theWord).get(theTag) + 1);

					if (!m_startCharFreq.containsKey(theFirstChar)) {
						m_startCharFreq.put(theFirstChar, 0);
						m_startCharPOSSets.put(theFirstChar,new HashMap<String, Integer>());
					}
					m_startCharFreq.put(theFirstChar,m_startCharFreq.get(theFirstChar) + 1);
					if (!m_startCharPOSSets.get(theFirstChar).containsKey(theTag)) {
						m_startCharPOSSets.get(theFirstChar).put(theTag, 0);
					}
					m_startCharPOSSets.get(theFirstChar).put(theTag,m_startCharPOSSets.get(theFirstChar).get(theTag) + 1);

					if (m_posCloseSet.containsKey(theTag)) {
						m_posCloseSet.get(theTag).add(theWord);
					}

					if (theSense.length() > 0) {
						String theFirstCharSense;
						if(theSense.indexOf("#") != -1){
							theFirstCharSense = theSense.substring(0, theSense.indexOf("#"));
						}else{
							theFirstCharSense = theSense;
						}
//						String theFirstCharSense = theSense.substring(0, 1);
						if (!m_wordFreq.containsKey(theSense)) {
							m_wordFreq.put(theSense, 0);
							m_wordPOSSets.put(theSense,	new HashMap<String, Integer>());
						}
						m_wordFreq.put(theSense, m_wordFreq.get(theWord) + 1);

						if (!m_wordPOSSets.get(theSense).containsKey(theTag)) {
							m_wordPOSSets.get(theSense).put(theTag, 0);
						}
						m_wordPOSSets.get(theSense).put(theSense,m_wordPOSSets.get(theSense).get(theTag) + 1);

						if (!m_startCharFreq.containsKey(theFirstCharSense)) {
							m_startCharFreq.put(theFirstCharSense, 0);
							m_startCharPOSSets.put(theFirstCharSense,new HashMap<String, Integer>());
						}
						m_startCharFreq.put(theFirstCharSense,m_startCharFreq.get(theFirstCharSense) + 1);
						if (!m_startCharPOSSets.get(theFirstCharSense).containsKey(theTag)) {
							m_startCharPOSSets.get(theFirstCharSense).put(theTag, 0);
						}
						m_startCharPOSSets.get(theFirstCharSense).put(theTag,
								m_startCharPOSSets.get(theFirstCharSense).get(theTag) + 1);

						if (m_posCloseSet.containsKey(theTag)) {
							m_posCloseSet.get(theTag).add(theSense);
						}
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * load file.
	 * 
	 * @param filename
	 * @return
	 */
	public int load(String filename) {
		int preRoundIndexForTrain = 0;
		newFeatureTemplates();
		m_posCloseSet = new HashMap<String, Set<String>>();
		m_wordPOSSets = new HashMap<String, Map<String, Integer>>();
		m_wordFreq = new HashMap<String, Integer>();
		m_startCharPOSSets = new HashMap<String, Map<String, Integer>>();
		m_startCharFreq = new HashMap<String, Integer>();

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename), "UTF-8"));
			String line = "";
			// String context = "";
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty())
					continue;
				String[] temstrs = line.trim().split("\\s+");
				if (temstrs.length == 1) {
					System.out.println("error line: " + line);
					continue;
				}
				if (temstrs[0].equals("weight") && temstrs.length == 6) {
					String name = temstrs[1].trim();
					double dweigth = Double.parseDouble(temstrs[2]);
					double dsum = Double.parseDouble(temstrs[3]);
					int iindex = (int) (Double.parseDouble(temstrs[4]));
					double aveWeight = Double.parseDouble(temstrs[5]);
					if (preRoundIndexForTrain == 0)
						preRoundIndexForTrain = iindex;

					String[] names = name.split("=");
					HashMap<String, Feature> hm = GetFeatureTemplate(names[0]);
					hm.put(name, new Feature(name, dweigth, dsum, iindex,
							aveWeight));
				} else if (temstrs[0].equals("worddict")
						&& temstrs.length % 2 == 1) {
					String theWord = temstrs[1];
					int wordfreq = Integer.parseInt(temstrs[2]);
					if (m_wordFreq.containsKey(theWord)) {
						System.out.println("model word dict word duplication: "
								+ theWord);
					}
					m_wordFreq.put(theWord, wordfreq);
					m_wordPOSSets.put(theWord, new HashMap<String, Integer>());
					int sumfreq = 0;
					for (int idx = 3; idx < temstrs.length - 1; idx++) {
						String thePOS = temstrs[idx];
						idx++;
						int curPOSFreq = Integer.parseInt(temstrs[idx]);
						sumfreq += curPOSFreq;
						if (m_wordPOSSets.get(theWord).containsKey(thePOS)) {
							System.out
									.println("model word dict pos duplication: "
											+ theWord + " " + thePOS);
						}
						m_wordPOSSets.get(theWord).put(thePOS, curPOSFreq);
					}
					if (sumfreq != wordfreq) {
						System.out
								.println("model word dict freq does not match: "
										+ theWord);
					}
				} else if (temstrs[0].equals("schardict")
						&& temstrs.length % 2 == 1 && temstrs[1].length() == 1) {
					String theWord = temstrs[1];
					int wordfreq = Integer.parseInt(temstrs[2]);
					if (m_startCharFreq.containsKey(theWord)) {
						System.out
								.println("model start char dict char duplication: "
										+ theWord);
					}
					m_startCharFreq.put(theWord, wordfreq);
					m_startCharPOSSets.put(theWord,
							new HashMap<String, Integer>());
					int sumfreq = 0;
					for (int idx = 3; idx < temstrs.length - 1; idx++) {
						String thePOS = temstrs[idx];
						idx++;
						int curPOSFreq = Integer.parseInt(temstrs[idx]);
						sumfreq += curPOSFreq;
						if (m_startCharPOSSets.get(theWord).containsKey(thePOS)) {
							System.out
									.println("model start char dict pos duplication: "
											+ theWord + " " + thePOS);
						}
						m_startCharPOSSets.get(theWord).put(thePOS, curPOSFreq);
					}
					if (sumfreq != wordfreq) {
						System.out
								.println("model start char dict freq does not match: "
										+ theWord);
					}
				} else if (temstrs[0].equals("closetag")) {
					String thePOS = temstrs[1];
					if (m_posCloseSet.containsKey(thePOS)) {
						System.out
								.println("model close tag dict POS duplication: "
										+ thePOS);
					}
					m_posCloseSet.put(thePOS, new HashSet<String>());
					for (int idx = 2; idx < temstrs.length; idx++) {
						if (m_posCloseSet.get(thePOS).contains(temstrs[idx])) {
							System.out
									.println("model close tag dict POS word duplication: "
											+ thePOS + " " + temstrs[idx]);
						}
						m_posCloseSet.get(thePOS).add(temstrs[idx]);
					}
				} else {
					System.out.println("error line: " + line);
				}

			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return preRoundIndexForTrain;
	}

	/**
	 * write to file.
	 * 
	 * @param filename
	 */
	public void save(String filename) {
		try {
			PrintWriter bw = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(filename), "UTF-8"));
			for (featureName f : featureName.values()) {
				HashMap<String, Feature> hm = GetFeatureTemplate(f.toString());
				for (String theKey : hm.keySet()) {
					Feature tempf = hm.get(theKey);
					bw.println("weight " + tempf.name + " "
							+ Double.toString(tempf.weight) + " "
							+ Double.toString(tempf.sum) + " "
							+ Double.toString(tempf.lastUpdateIndex) + " "
							+ Double.toString(tempf.aveWeight));
					;
				}
			}

			for (String theKey : m_wordFreq.keySet()) {
				String outline = "worddict\t" + theKey + " "
						+ Integer.toString(m_wordFreq.get(theKey));
				for (String thePOSKey : m_wordPOSSets.get(theKey).keySet()) {
					outline = outline
							+ " "
							+ thePOSKey
							+ " "
							+ Integer.toString(m_wordPOSSets.get(theKey).get(
									thePOSKey));
				}
				bw.println(outline);
			}

			for (String theKey : m_startCharFreq.keySet()) {
				String outline = "schardict\t" + theKey + " "
						+ Integer.toString(m_startCharFreq.get(theKey));
				for (String thePOSKey : m_startCharPOSSets.get(theKey).keySet()) {
					outline = outline
							+ " "
							+ thePOSKey
							+ " "
							+ Integer.toString(m_startCharPOSSets.get(theKey)
									.get(thePOSKey));
				}
				bw.println(outline);
			}

			for (String theKey : m_posCloseSet.keySet()) {
				String outline = "closetag\t" + theKey;
				for (String theWordKey : m_posCloseSet.get(theKey)) {
					outline = outline + " " + theWordKey;
				}
				bw.println(outline);
			}

			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// ����FileWriter��������д���ַ���

	}

	/**
	 * update weight.
	 * 
	 * @param oprFeatures
	 * @param iType
	 * @param updateIndex
	 */

	public void UpdateWeighth(List<String> oprFeatures, int iType,
			int updateIndex) {
		double tempiType = iType;
		double dType = iType;
		for (String curFeature : oprFeatures) {
			int _index = curFeature.indexOf("=");
			String sTemplateName = curFeature.substring(0, _index);
			HashMap<String, Feature> hm = GetFeatureTemplate(sTemplateName);
			Feature temp = hm.get(curFeature);		
			if (temp != null) {
				if (temp.lastUpdateIndex < updateIndex) {
					temp.sum += (updateIndex - temp.lastUpdateIndex) * temp.weight;
				}
				temp.weight += iType;
				temp.sum += iType;
				temp.lastUpdateIndex = updateIndex;
				hm.put(curFeature, temp);
			} else {			
				hm.put(curFeature, new Feature(curFeature, (double) iType,(double) iType, updateIndex, 0.0));
			}
		}
	}

	/**
	 * computer the average of weights.
	 * 
	 * @param curRoundIndexForTrain
	 */

	public void AveWeight(int curRoundIndexForTrain) {
		for (featureName f : featureName.values()) {
//			System.out.println("f=" + f);
			HashMap<String, Feature> hm = GetFeatureTemplate(f.toString());
			Iterator iter = hm.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Feature> entry = (Map.Entry<String, Feature>) iter
						.next();
				String key = entry.getKey();
				Feature tempf = entry.getValue();
				if (tempf.lastUpdateIndex < curRoundIndexForTrain) {
					tempf.sum += (curRoundIndexForTrain - tempf.lastUpdateIndex)
							* tempf.weight;
					tempf.lastUpdateIndex = curRoundIndexForTrain;
				}
				tempf.aveWeight = tempf.sum / (curRoundIndexForTrain);
				hm.put(key, tempf);

			}
		}
	}

	public HashMap<String, Feature> GetFeatureTemplate(String featureTemplate) {
		// System.out.println(featureTemplate);
		featureName aa = featureName.valueOf(featureName.class, featureTemplate);//return featureTemplate对应的枚举类型的常量

		switch (aa) {
		case OrgCharUnigram:
			return m_mapOrgCharUnigram;
		case OrgCharBigram:
			return m_mapOrgCharBigram;
		case OrgCharTrigram:
			return m_mapOrgCharTrigram;
		case OrgSeenWords:
			return m_mapOrgSeenWords;
		case OrgLastWordByWord:
			return m_mapOrgLastWordByWord;
		case OrgCurrentWordLastChar:
			return m_mapOrgCurrentWordLastChar;
		case OrgLastWordFirstChar:
			return m_mapOrgLastWordFirstChar;

		case OrgFirstCharLastWordByWord:
			return m_mapOrgFirstCharLastWordByWord;
		case OrgLastWordByLastChar:
			return m_mapOrgLastWordByLastChar;
		case OrgSeparateChars:
			return m_mapOrgSeparateChars;
		case OrgConsecutiveWords:
			return m_mapOrgConsecutiveWords;
		case OrgFirstAndLastChars:
			return m_mapOrgFirstAndLastChars;
		case OrgOneCharWord:
			return m_mapOrgOneCharWord;
		case OrgLengthByFirstChar:
			return m_mapOrgLengthByFirstChar;
		case OrgLengthByLastChar:
			return m_mapOrgLengthByLastChar;
		case OrgLengthByLastWord:
			return m_mapOrgLengthByLastWord;
		case OrgLastLengthByWord:
			return m_mapOrgLastLengthByWord;
		case OrgCurrentTag:
			return m_mapOrgCurrentTag;

		case OrgLastTagByTag:
			return m_mapOrgLastTagByTag;
		case OrgLastTwoTagsByTag:
			return m_mapOrgLastTwoTagsByTag;
		case OrgTagByLastWord:
			return m_mapOrgTagByLastWord;
		case OrgLastTagByWord:
			return m_mapOrgLastTagByWord;
		case OrgTagByFirstChar:
			return m_mapOrgTagByFirstChar;
		case OrgTagByLastChar:
			return m_mapOrgTagByLastChar;
		case OrgTagByChar:
			return m_mapOrgTagByChar;
		case OrgTagOfOneCharWord:
			return m_mapOrgTagOfOneCharWord;
		case OrgRepeatedCharByTag:
			return m_mapOrgRepeatedCharByTag;
		case OrgTagByWordAndPrevChar:
			return m_mapOrgTagByWordAndPrevChar;
		case OrgTagByWordAndNextChar:
			return m_mapOrgTagByWordAndNextChar;
		case OrgTaggedCharByFirstChar:
			return m_mapOrgTaggedCharByFirstChar;
		case OrgTaggedCharByFirstTaggedChar: //c_0t_0c_1_t_1
		return m_mapOrgTaggedCharByFirstTaggedChar;
		
		case OrgTaggedCharByLastChar:
			return m_mapOrgTaggedCharByLastChar;
		case OrgTaggedSeparateChars:
			return m_mapOrgTaggedSeparateChars;
		case OrgTaggedConsecutiveChars:
			return m_mapOrgTaggedConsecutiveChars;
		case OrgWordTagTag:
			return m_mapOrgWordTagTag;
		case OrgTagWordTag:
			return m_mapOrgTagWordTag;

		case OrgFirstCharBy2Tags:
			return m_mapOrgFirstCharBy2Tags;
		case OrgFirstCharBy3Tags:
			return m_mapOrgFirstCharBy3Tags;
		case OrgFirstCharAndChar:
			return m_mapOrgFirstCharAndChar;
		case OrgSepCharAndNextChar:
			return m_mapOrgSepCharAndNextChar;
		case OrgAppCharAndNextChar:
			return m_mapOrgAppCharAndNextChar;
		case OrgPartialLengthByFirstChar:
			return m_mapOrgPartialLengthByFirstChar;
		case OrgLengthByTagAndFirstChar:
			return m_mapOrgLengthByTagAndFirstChar;
		case OrgLengthByTagAndLastChar:
			return m_mapOrgLengthByTagAndLastChar;
		case OrgTag0Tag1Size1:
			return m_mapOrgTag0Tag1Size1;
		case OrgTag1Tag2Size1:
			return m_mapOrgTag1Tag2Size1;
		case OrgTag0Tag1Tag2Size1:
			return m_mapOrgTag0Tag1Tag2Size1;		
	
		case OrgPartialWord:
			return m_mapOrgPartialWord;
		
			// normalization
		case NorCharUnigram:
			return m_mapNorCharUnigram;
		case NorCharBigram:
			return m_mapNorCharBigram;
		case NorCharTrigram:
			return m_mapNorCharTrigram;
		case NorSeenWords:
			return m_mapNorSeenWords;
		case NorLastWordByWord:
			return m_mapNorLastWordByWord;
		case NorCurrentWordLastChar:
			return m_mapNorCurrentWordLastChar;
		case NorLastWordFirstChar:
			return m_mapNorLastWordFirstChar;

		case NorFirstCharLastWordByWord:
			return m_mapNorFirstCharLastWordByWord;
		case NorLastWordByLastChar:
			return m_mapNorLastWordByLastChar;
		case NorSeparateChars:
			return m_mapNorSeparateChars;
		case NorConsecutiveChars:
			return m_mapNorConsecutiveChars;
		case NorFirstAndLastChars:
			return m_mapNorFirstAndLastChars;
		case NorOneCharWord:
			return m_mapNorOneCharWord;
		case NorLengthByFirstChar:
			return m_mapNorLengthByFirstChar;
		case NorLengthByLastChar:
			return m_mapNorLengthByLastChar;
		case NorLengthByLastWord:
			return m_mapNorLengthByLastWord;
		case NorLastLengthByWord:
			return m_mapNorLastLengthByWord;
		case NorCurrentTag:
			return m_mapNorCurrentTag;

		case NorLastTagByTag:
			return m_mapNorLastTagByTag;
		case NorLastTwoTagsByTag:
			return m_mapNorLastTwoTagsByTag;
		case NorTagByLastWord:
			return m_mapNorTagByLastWord;
		case NorLastTagByWord:
			return m_mapNorLastTagByWord;
		case NorTagByFirstChar:
			return m_mapNorTagByFirstChar;
		case NorTagByLastChar:
			return m_mapNorTagByLastChar;
		case NorTagByChar:
			return m_mapNorTagByChar;
		case NorTagOfOneCharWord:
			return m_mapNorTagOfOneCharWord;
		case NorRepeatedCharByTag:
			return m_mapNorRepeatedCharByTag;
		case NorTagByWordAndPrevChar:
			return m_mapNorTagByWordAndPrevChar;
		case NorTagByWordAndNextChar:
			return m_mapNorTagByWordAndNextChar;
		case NorTaggedCharByFirstChar:
			return m_mapNorTaggedCharByFirstChar;
		case NorTaggedCharByLastChar:
			return m_mapNorTaggedCharByLastChar;
		case NorTaggedSeparateChars:
			return m_mapNorTaggedSeparateChars;
		case NorTaggedConsecutiveChars:
			return m_mapNorTaggedConsecutiveChars;
		case NorWordTagTag:
			return m_mapNorWordTagTag;
		case NorTagWordTag:
			return m_mapNorTagWordTag;

		case NorFirstCharBy2Tags:
			return m_mapNorFirstCharBy2Tags;
		case NorFirstCharBy3Tags:
			return m_mapNorFirstCharBy3Tags;
		case NorFirstCharAndChar:
			return m_mapNorFirstCharAndChar;
		case NorSepCharAndNextChar:
			return m_mapNorSepCharAndNextChar;
		case NorAppCharAndNextChar:
			return m_mapNorAppCharAndNextChar;
		case NorPartialLengthByFirstChar:
			return m_mapNorPartialLengthByFirstChar;
		case NorLengthByTagAndFirstChar:
			return m_mapNorLengthByTagAndFirstChar;
		case NorLengthByTagAndLastChar:
			return m_mapNorLengthByTagAndLastChar;
		case NorTag0Tag1Size1:
			return m_mapNorTag0Tag1Size1;
		case NorTag1Tag2Size1:
			return m_mapNorTag1Tag2Size1;
		case NorTag0Tag1Tag2Size1:
			return m_mapNorTag0Tag1Tag2Size1;	

			// sense feature templates
		case WordSense:
			return m_mapWordSense; // w1s1
		case LastWordAndWordSense:
			return m_mapLastWordAndWordSense; // w1s1w0
		case PreWordAndWordSense:
			return m_mapPreWordAndWordSense; // w1s1w2
		case StartPreAndWordSense:
			return m_mapStartPreAndWordSense; // w1s1statr_w2
		case EndPreAndWordSense:
			return m_mapEndPreAndWordSense; // w1s1end_w2
		case StartLastAndWordSense:
			return m_mapStartLastAndWordSense; // w1s1start_w0
		case LastCharAndWordSense:
			return m_mapLastCharAndWordSense; // w1s1C0
		case TagWordSense:
			return m_mapTagWordSense; // w1s1t1
		case PreTagAndWordSense:
			return m_mapPreTagAndWordSense; // w1s1t2
		case LastTagAndWordSense:
			return m_mapLastTagAndWordSense; // w1s1t0
		case ThreeWordAndSense:
			return m_mapThreeWordAndSense; // w1s1w0w2
		case PreLastTagAndWordSense:
			return m_mapPreLastTagAndWordSense; // w1s1t0t2
		case TwoWordSense:
			return m_mapTwoWordSense; // w1s1w2s2
		case LastTagAndTwoWordSense:
			return m_mapLastTagAndTwoWordSense; // w1s1w2s2t0
		case LastWordAndTwoWordSense:
			return m_mapLastWordAndTwoWordSense; // w1s1w2s2w0
		case LastTagAndPreWordSense:
			return m_mapLastTagAndPreWordSense; // w2s2t0
		case LastWordAndPreWordSense:
			return m_mapLastWordAndPreWordSense; // w2s2w0
		case LastCharAndPreWordSense:
			return m_mapLastCharAndPreWordSense; // w3s2c0

		case Gram2:
			return m_mapGram2;
		case Gram3:
			return m_mapGram3;
		case Gram4:
			return m_mapGram4;
			
		case OrgIsDisease:
			return m_mapOrgIsDisease;
		case OrgIsPartialDisease:
			return m_mapOrgIsPartialDisease;
		case OrgPOSConsecutiveChars :
			return m_mapOrgPOSConsecutiveChars ;
		case OrgPreWordPos:
			return m_mapOrgPreWordPos;
			
		//POS			
		case OrgTagCharPOS: //t_0+ c_0+ p_0
		return	m_mapOrgTagCharPOS;
		case OrgTagPOS://t_0 + p_0 
			return m_mapOrgTagPOS;
		case OrgCharPOSTagBigram ://t_0 + c_0 + p_0 + p_1
			return m_mapOrgCharPOSTagBigram ;
		case OrgCharPOSTagTrigram : //t_0 + c_0 + p_0 +p_1 + p_2
			return m_mapOrgCharPOSTagTrigram;
		case OrgTagsPOSBigram ://t_0 + t_1 + p_1 + p_0
			return m_mapOrgTagsPOSBigram;
		case OrgTagsPOSTrigram:// t_0 + t_1 + t_2 + p_0 + p_1 + p_2
			return m_mapOrgTagsPOSTrigram;
		case OrgTagPOSBigram : //t_0 + p_1 + p_0
			return m_mapOrgTagPOSBigram;
		case OrgTagPOSTrigram : // t_0  + p_0 + p_1 + p_2
			return m_mapOrgTagPOSTrigram ;
		
		case OrgCharsAndPOSAndTags : 
			return m_mapOrgCharsAndPOSAndTags;
		case OrgConsecutivePOSAndConsecutiveTags :
			return m_mapOrgConsecutivePOSAndConsecutiveTags;
		case OrgConsecutiveCharsAndConsecutivePOS:
			return m_mapOrgConsecutiveCharsAndConsecutivePOS;
		case OrgPOSCharByFirstChar:
			return m_mapOrgPOSCharByFirstChar;
		case OrgConsecutiveCharsAndPOS:
			return m_mapOrgConsecutiveCharsAndPOS;
		//prefix,suffix
		case OrgCharPrefix:
			return m_mapOrgCharPrefix;
		case OrgCharSuffix:
		return m_mapOrgCharSuffix;
		//brown clustering 
		case OrgLastCharBrown:
			return m_mapOrgLastCharBrown;
	   //wordVector
		case OrgLastCharVector:
		return m_mapOrgLastCharVector;
		//abbreviation
		case  OrgIsAbbre:
		return 	m_mapOrgIsAbbre;
		//stem
		case OrgStemBigram:
		return m_mapOrgStemBigram;
		case OrgStemLabel: //s_0 + l_0
			return m_mapOrgStemLabel;
		//bag of words
		case OrgWordAfterWord:
		return m_mapOrgWordAfterWord;
		case OrgBiWordAfterWord:
		return m_mapOrgBiWordAfterWord;
		case OrgBeforeWordAndAfterWord:
		return m_mapOrgBeforeWordAndAfterWord;	
		
		//norm + after word
		case NorSeenWordsAfterWord://  n_1 + w1;	
			return m_mapNorSeenWordsAfterWord;
		case NorLastWordByWordAfterWord:
		   return m_mapNorLastWordByWordAfterWord;// + n_1 + "_" + n_2 + w1;
		case NorFirstAndLastCharsAfterWord:
			return 	m_mapNorFirstAndLastCharsAfterWord;// + start_n_1 + end_n_1 + w1;
		case 	NorLengthByLastCharAfterWord:
			return 	m_mapNorLengthByLastCharAfterWord;// + end_n_1 + len_n_1 + w1;
		case NorLengthByFirstCharAfterWord:
			return m_mapNorLengthByFirstCharAfterWord;// + start_n_1 + len_n_1 + w1;		
		case NorCurrentWordLastCharAfterWord:
		return m_mapNorCurrentWordLastCharAfterWord;// + end_n_2 + "_" + n_1 + w1;
		case NorLastWordByLastCharAfterWord:
		return m_mapNorLastWordByLastCharAfterWord;// + end_n_2 + end_n_1 + w1;
		case NorLengthByLastWordAfterWord:
			return m_mapNorLengthByLastWordAfterWord;// + n_2 + len_n_1 + w1;
		case NorLastLengthByWordAfterWord:
			return 	m_mapNorLastLengthByWordAfterWord;// + len_n_2 + n_1 + w1;
		case NorCurrentTagAfterWord:
			return 	m_mapNorCurrentTagAfterWord;// + n_1 + l_1 + w1;
		case NorTagByWordAndPrevCharAfterWord:
			return m_mapNorTagByWordAndPrevCharAfterWord;// + n_1 + l_1 + end_n_2 + w1;
		case NorTagByLastWordAfterWord:
			return m_mapNorTagByLastWordAfterWord;// + n_1 + l_0 + w1;
		case NorLastTagByWordAfterWord:
			return m_mapNorLastTagByWordAfterWord;// + n_1 + l_2 + w1;
		case NorTagByLastCharAfterWord:
			return m_mapNorTagByLastCharAfterWord;// + end_n_1 + l_1 + w1;
		case NorLastTagAfterWord:
			return m_mapNorLastTagAfterWord;// + l_1 + w1;
		case NorTag0Tag1Size1AfterWord:
			return 	m_mapNorTag0Tag1Size1AfterWord;// + l_1 + l_0 + len_n_1 + w1;
		case NorTag1Tag2Size1AfterWord:
			return m_mapNorTag1Tag2Size1AfterWord;// + l_2 + l_1 + len_n_1 + w1;
		case NorTag0Tag1Tag2Size1AfterWord:
			return m_mapNorTag0Tag1Tag2Size1AfterWord;// + l_2 + l_1 + l_0 + len_n_1 + w1;
		case NorLastTwoTagsByTagAfterWord:
			return m_mapNorLastTwoTagsByTagAfterWord;// + l_2 + l_1 + l_0 + w1;
		case NorTagByCharAfterWord:
			return m_mapNorTagByCharAfterWord;// + l_0 + w_0 + w1;
		case NorFirstCharBy2TagsAfterWord:
			return m_mapNorFirstCharBy2TagsAfterWord;// + l_0 + l_1 + w_0 + w1;
		case NorSeparateCharsAfterWord:
			return m_mapNorSeparateCharsAfterWord;// + w_1 + w_0 + w1;
		case NorLastWordFirstCharAfterWord:
			return m_mapNorLastWordFirstCharAfterWord;// + n_1 + "_" + w_0 + w1;
		case NorFirstCharLastWordByWordAfterWord:
			return m_mapNorFirstCharLastWordByWordAfterWord;// + start_n_1 + w_0 + w1;
		case NorTagWordTagAfterWord:
			return m_mapNorTagWordTagAfterWord;// + l_2 + n_1 + l_0 + w1;
		case NorWordTagTagAfterWord:
			return m_mapNorWordTagTagAfterWord;// + n_2 + l_1 + l_0 + w1;
		case NorTagByFirstCharAfterWord:
			return m_mapNorTagByFirstCharAfterWord;//start_n_1 + l_1 + w1;
		case NorFirstAndLastCharsAndLabelAfterWord: //start_n_1 + end_n_1 + l_1 + w1;
		   return m_mapNorFirstAndLastCharsAndLabelAfterWord;
		
		case NorSeenWordsAfterWords:
		return  m_mapNorSeenWordsAfterWords; // + n_1 + w1 + w2
		case NorCurrentTagAfterWords:
		return	m_mapNorCurrentTagAfterWords;//+ n_1 +l_1+ w1 +w2
		case NorFirstAndLastCharsAndLabelAfterWords:
		return	m_mapNorFirstAndLastCharsAndLabelAfterWords;// + start_n_1 + end_n_1 + l_1 + w1 +w2
		case NorFirstAndLastCharsAfterWords:
		return	m_mapNorFirstAndLastCharsAfterWords;// + start_n_1 + end_n_1 + w1 + w2
		case NorLengthByLastCharAfterWords:
		return	m_mapNorLengthByLastCharAfterWords;// + end_n_1 + len_n_1 + w1 + w2
		case NorLastWordByLastCharAfterWords:
		return	m_mapNorLastWordByLastCharAfterWords;//+ end_n_2 + end_n_1 + w1 + w2
		case NorTagByLastCharAfterWords:
		return	m_mapNorTagByLastCharAfterWords;//+ end_n_1 + l_1 + w1 + w2
		case NorLastTagAfterWords:
		return	m_mapNorLastTagAfterWords;//l_1 + w1 + w2 
		   
		   
		}
			
		return null;
	}
	// feature template
	public enum featureName {
		OrgCharUnigram, OrgCharBigram, OrgCharTrigram, OrgSeenWords, OrgLastWordByWord, OrgCurrentWordLastChar, OrgLastWordFirstChar,
		OrgFirstCharLastWordByWord, OrgLastWordByLastChar, OrgSeparateChars, OrgConsecutiveWords, OrgFirstAndLastChars, OrgOneCharWord, OrgLengthByFirstChar, OrgLengthByLastChar, OrgLengthByLastWord, OrgLastLengthByWord, OrgCurrentTag,
		OrgLastTagByTag, OrgLastTwoTagsByTag, OrgTagByLastWord, OrgLastTagByWord, OrgTagByFirstChar, OrgTagByLastChar, OrgTagByChar, OrgTagOfOneCharWord, OrgRepeatedCharByTag, OrgTagByWordAndPrevChar, OrgTagByWordAndNextChar, OrgTagWordTag, OrgTaggedCharByFirstChar,OrgTaggedCharByFirstTaggedChar,
		OrgTaggedCharByLastChar, OrgTaggedSeparateChars, OrgTaggedConsecutiveChars, OrgWordTagTag, 
		OrgFirstCharBy2Tags, OrgFirstCharBy3Tags, OrgFirstCharAndChar, OrgSepCharAndNextChar, OrgAppCharAndNextChar, OrgPartialWord, OrgPartialLengthByFirstChar, OrgLengthByTagAndFirstChar, OrgLengthByTagAndLastChar, OrgTag0Tag1Size1, OrgTag1Tag2Size1, OrgTag0Tag1Tag2Size1, NorCharUnigram, NorCharBigram, NorCharTrigram, NorSeenWords, NorLastWordByWord, NorCurrentWordLastChar, NorLastWordFirstChar, NorFirstCharLastWordByWord, NorLastWordByLastChar, NorSeparateChars, NorConsecutiveChars, NorFirstAndLastChars, NorOneCharWord, NorLengthByFirstChar, NorLengthByLastChar, NorLengthByLastWord, NorLastLengthByWord, NorCurrentTag, 
		NorLastTagByTag, NorLastTwoTagsByTag, NorTagByLastWord, NorLastTagByWord, NorTagByFirstChar, NorTagByLastChar, NorTagByChar, NorTagOfOneCharWord, NorRepeatedCharByTag, NorTagByWordAndPrevChar, NorTagByWordAndNextChar, NorTagWordTag, NorTaggedCharByFirstChar, NorTaggedCharByLastChar, NorTaggedSeparateChars, NorTaggedConsecutiveChars, NorWordTagTag, NorFirstCharBy2Tags, NorFirstCharBy3Tags, NorFirstCharAndChar, NorSepCharAndNextChar, NorAppCharAndNextChar,  NorPartialLengthByFirstChar, NorLengthByTagAndFirstChar, NorLengthByTagAndLastChar, NorTag0Tag1Size1, NorTag1Tag2Size1, NorTag0Tag1Tag2Size1,
        WordSense, LastWordAndWordSense, PreWordAndWordSense, StartPreAndWordSense, EndPreAndWordSense, StartLastAndWordSense, LastCharAndWordSense, TagWordSense, PreTagAndWordSense, LastTagAndWordSense, ThreeWordAndSense, PreLastTagAndWordSense, TwoWordSense, LastTagAndTwoWordSense, LastWordAndTwoWordSense,
        LastTagAndPreWordSense, LastWordAndPreWordSense, LastCharAndPreWordSense, Gram2, Gram3, Gram4,OrgIsDisease,OrgIsPartialDisease,OrgPOSConsecutiveChars,OrgPreWordPos,OrgTagCharPOS,OrgTagPOS,OrgCharPOSTagBigram,OrgCharPOSTagTrigram,OrgTagsPOSBigram,OrgTagsPOSTrigram,OrgTagPOSBigram,OrgTagPOSTrigram,OrgCharsAndPOSAndTags,OrgConsecutivePOSAndConsecutiveTags,OrgConsecutiveCharsAndConsecutivePOS,OrgPOSCharByFirstChar,OrgConsecutiveCharsAndPOS,OrgCharPrefix,
		OrgCharSuffix, OrgLastCharBrown, OrgLastCharVector, OrgIsAbbre, OrgStemBigram,OrgStemLabel, OrgWordAfterWord, OrgBiWordAfterWord,OrgBeforeWordAndAfterWord, NorLastWordByWordAfterWord,	NorFirstAndLastCharsAfterWord,NorLengthByLastCharAfterWord,NorLengthByFirstCharAfterWord,
		NorSeenWordsAfterWord,NorCurrentWordLastCharAfterWord, NorLastWordByLastCharAfterWord, NorLengthByLastWordAfterWord, NorLastLengthByWordAfterWord, NorCurrentTagAfterWord, NorTagByWordAndPrevCharAfterWord, NorTagByLastWordAfterWord, NorLastTagByWordAfterWord, NorTagByLastCharAfterWord,NorLastTagAfterWord, NorTag0Tag1Size1AfterWord, NorTag1Tag2Size1AfterWord,
		NorLastTwoTagsByTagAfterWord, NorTag0Tag1Tag2Size1AfterWord, NorTagByCharAfterWord, NorFirstCharBy2TagsAfterWord, NorSeparateCharsAfterWord, NorLastWordFirstCharAfterWord, NorFirstCharLastWordByWordAfterWord, NorTagWordTagAfterWord, NorWordTagTagAfterWord, NorTagByFirstCharAfterWord,NorFirstAndLastCharsAndLabelAfterWord, 
		NorSeenWordsAfterWords, NorCurrentTagAfterWords, NorFirstAndLastCharsAndLabelAfterWords, NorFirstAndLastCharsAfterWords, NorLengthByLastCharAfterWords, NorLastWordByLastCharAfterWords, NorTagByLastCharAfterWords,NorLastTagAfterWords;
	}

	// instantiation features
	public void newFeatureTemplates() {
		// feature templates abstd::cout characters
		m_mapNorCharUnigram = new HashMap<String, Feature>(); // C0
		m_mapOrgCharBigram = new HashMap<String, Feature>(); // C-1C0
		m_mapOrgCharTrigram = new HashMap<String, Feature>(); // C-2C-1C0

		// feature templates abstd::cout words
		m_mapOrgSeenWords = new HashMap<String, Feature>(); // w_1
		m_mapOrgLastWordByWord = new HashMap<String, Feature>(); // w-2w-1
		m_mapOrgCurrentWordLastChar = new HashMap<String, Feature>(); // w_1_end_w_2
		m_mapOrgLastWordFirstChar = new HashMap<String, Feature>(); // w_1_c_0
		m_mapOrgFirstCharLastWordByWord = new HashMap<String, Feature>(); // start_w_1_C_0
		m_mapOrgLastWordByLastChar = new HashMap<String, Feature>(); // w_1_c_0_t_1
		m_mapOrgSeparateChars = new HashMap<String, Feature>();// end_w_1_c_0
		m_mapOrgConsecutiveWords = new HashMap<String, Feature>();// word_bigram
																	// for app
		m_mapOrgFirstAndLastChars = new HashMap<String, Feature>(); // start_w_1end_w_1
		m_mapOrgOneCharWord = new HashMap<String, Feature>();// w-1
																// if(len_w-1==1)
		m_mapOrgLengthByFirstChar = new HashMap<String, Feature>();// start_w_1_len_w_1
		m_mapOrgLengthByLastChar = new HashMap<String, Feature>();// end_w_1_len_w_1
		m_mapOrgLengthByLastWord = new HashMap<String, Feature>();// w_2_len_w_1
		m_mapOrgLastLengthByWord = new HashMap<String, Feature>();// w_1_len_w_2

		// feature templates tag
		m_mapOrgCurrentTag = new HashMap<String, Feature>(); // w_1_t_1
		m_mapOrgLastTagByTag = new HashMap<String, Feature>(); // t-1t0
		m_mapOrgLastTwoTagsByTag = new HashMap<String, Feature>(); // t-2t-1t0
		m_mapOrgTagByLastWord = new HashMap<String, Feature>(); // w-1t0
		m_mapOrgLastTagByWord = new HashMap<String, Feature>(); // w-1t-2
		m_mapOrgTagByFirstChar = new HashMap<String, Feature>();// first_char_0,
																// tag_0
		m_mapOrgTagByLastChar = new HashMap<String, Feature>();// end_w_1_t_1
		m_mapOrgTagByChar = new HashMap<String, Feature>();// (first_char_0,
															// tag_0 for two
															// action
		m_mapOrgTagOfOneCharWord = new HashMap<String, Feature>();// end_w_2_w_1_c_0
																	// if
																	// len_w_1=1
		m_mapOrgRepeatedCharByTag = new HashMap<String, Feature>();
		m_mapOrgTagByWordAndPrevChar = new HashMap<String, Feature>();// w_1_end_w_2_t_1
		m_mapOrgTagByWordAndNextChar = new HashMap<String, Feature>();
		m_mapOrgTaggedCharByFirstChar = new HashMap<String, Feature>();// first_char
																		// char_unigram,
																		// tag_0
																		// for
																		// app
		m_mapOrgTaggedCharByFirstTaggedChar = new HashMap<String,Feature>(); //c_0t_0c_1t_1 for app
		m_mapOrgTaggedCharByLastChar = new HashMap<String, Feature>();// w_1��char��last_char

		// extra features
		m_mapOrgTaggedSeparateChars = new HashMap<String, Feature>();// last_char_1,
																		// tag_1,
																		// first_char_0,
																		// tag_0
		m_mapOrgTaggedConsecutiveChars = new HashMap<String, Feature>();// char_bigram,
																		// tag_0
																		// for
																		// app

		m_mapOrgWordTagTag = new HashMap<String, Feature>();// word_2,
															// tag_0_tag_1
		m_mapOrgTagWordTag = new HashMap<String, Feature>();// word_1,
															// tag_0_tag_2
		m_mapOrgFirstCharBy2Tags = new HashMap<String, Feature>();// first_char_0,
																	// tag_0_tag_1
		m_mapOrgFirstCharBy3Tags = new HashMap<String, Feature>();// first_char_0,
																	// tag_0_tag_1_tag_2
		m_mapOrgFirstCharAndChar = new HashMap<String, Feature>();

		m_mapOrgSepCharAndNextChar = new HashMap<String, Feature>();
		m_mapOrgAppCharAndNextChar = new HashMap<String, Feature>();

		m_mapOrgPartialWord = new HashMap<String, Feature>();
		m_mapOrgPartialLengthByFirstChar = new HashMap<String, Feature>();
		m_mapOrgLengthByTagAndFirstChar = new HashMap<String, Feature>();
		m_mapOrgLengthByTagAndLastChar = new HashMap<String, Feature>();

		m_mapOrgTag0Tag1Size1 = new HashMap<String, Feature>();// t_1_t_0_len(w_1)
		m_mapOrgTag1Tag2Size1 = new HashMap<String, Feature>();// t_2_t_1_len(w_1)
		m_mapOrgTag0Tag1Tag2Size1 = new HashMap<String, Feature>();// t_1_t_0_len(w_1)

	
	

		// normalization features
		m_mapNorCharUnigram = new HashMap<String, Feature>(); // C0
		m_mapNorCharBigram = new HashMap<String, Feature>(); // C-1C0
		m_mapNorCharTrigram = new HashMap<String, Feature>(); // C-2C-1C0

		// feature templates abstd::cout words
		m_mapNorSeenWords = new HashMap<String, Feature>(); // w_1
		m_mapNorLastWordByWord = new HashMap<String, Feature>(); // w-2w-1
		m_mapNorCurrentWordLastChar = new HashMap<String, Feature>();// w_1_end_w_2
		m_mapNorLastWordFirstChar = new HashMap<String, Feature>();// w_1_c_0
		m_mapNorFirstCharLastWordByWord = new HashMap<String, Feature>();// start_w_1_C_0
		m_mapNorLastWordByLastChar = new HashMap<String, Feature>();// w_1_c_0_t_1
		m_mapNorSeparateChars = new HashMap<String, Feature>();// end_w_1_c_0
		m_mapNorConsecutiveChars = new HashMap<String, Feature>();// char_bigram
																	// for app
		m_mapNorFirstAndLastChars = new HashMap<String, Feature>(); // start_w_1end_w_1
		m_mapNorOneCharWord = new HashMap<String, Feature>();// w-1
																// if(len_w-1==1)
		m_mapNorLengthByFirstChar = new HashMap<String, Feature>();// start_w_1_len_w_1
		m_mapNorLengthByLastChar = new HashMap<String, Feature>();// end_w_1_len_w_1
		m_mapNorLengthByLastWord = new HashMap<String, Feature>();// w_2_len_w_1
		m_mapNorLastLengthByWord = new HashMap<String, Feature>();// w_1_len_w_2

		// feature templates tag
		m_mapNorCurrentTag = new HashMap<String, Feature>(); // w_1_t_1
		m_mapNorLastTagByTag = new HashMap<String, Feature>(); // t-1t0
		m_mapNorLastTwoTagsByTag = new HashMap<String, Feature>(); // t-2t-1t0
		m_mapNorTagByLastWord = new HashMap<String, Feature>(); // w-1t0
		m_mapNorLastTagByWord = new HashMap<String, Feature>(); // w-1t-2
		m_mapNorTagByFirstChar = new HashMap<String, Feature>();// first_char_0,
																// tag_0
		m_mapNorTagByLastChar = new HashMap<String, Feature>();// end_w_1_t_1
		m_mapNorTagByChar = new HashMap<String, Feature>();// (first_char_0,
															// tag_0 for two
															// action
		m_mapNorTagOfOneCharWord = new HashMap<String, Feature>();// end_w_2_w_1_c_0
																	// if
																	// len_w_1=1
		m_mapNorRepeatedCharByTag = new HashMap<String, Feature>();
		m_mapNorTagByWordAndPrevChar = new HashMap<String, Feature>();// w_1_end_w_2_t_1
		m_mapNorTagByWordAndNextChar = new HashMap<String, Feature>();
		m_mapNorTaggedCharByFirstChar = new HashMap<String, Feature>();// first_char
																		// char_unigram,
																		// tag_0
																		// for
																		// app
		m_mapNorTaggedCharByLastChar = new HashMap<String, Feature>();// w_1��char��last_char

		// extra features
		m_mapNorTaggedSeparateChars = new HashMap<String, Feature>();// last_char_1,
																		// tag_1,
																		// first_char_0,
																		// tag_0
		m_mapNorTaggedConsecutiveChars = new HashMap<String, Feature>();// char_bigram,
																		// tag_0
																		// for
																		// app

		m_mapNorWordTagTag = new HashMap<String, Feature>();// word_2,
															// tag_0_tag_1
		m_mapNorTagWordTag = new HashMap<String, Feature>();// word_1,
															// tag_0_tag_2
		m_mapNorFirstCharBy2Tags = new HashMap<String, Feature>();// first_char_0,
																	// tag_0_tag_1
		m_mapNorFirstCharBy3Tags = new HashMap<String, Feature>();// first_char_0,
																	// tag_0_tag_1_tag_2
		m_mapNorFirstCharAndChar = new HashMap<String, Feature>();

		m_mapNorSepCharAndNextChar = new HashMap<String, Feature>();
		m_mapNorAppCharAndNextChar = new HashMap<String, Feature>();

		m_mapNorPartialWord = new HashMap<String, Feature>();
		m_mapNorPartialLengthByFirstChar = new HashMap<String, Feature>();
		m_mapNorLengthByTagAndFirstChar = new HashMap<String, Feature>();
		m_mapNorLengthByTagAndLastChar = new HashMap<String, Feature>();

		m_mapNorTag0Tag1Size1 = new HashMap<String, Feature>();// t_1_t_0_len(w_1)
		m_mapNorTag1Tag2Size1 = new HashMap<String, Feature>();// t_2_t_1_len(w_1)
		m_mapNorTag0Tag1Tag2Size1 = new HashMap<String, Feature>();// t_1_t_0_len(w_1)

		// feature templates knowledge
		m_mapNorTagByFirstCharCat = new HashMap<String, Feature>();// first_char_cat_0,
																	// tag_0
		m_mapNorTagByLastCharCat = new HashMap<String, Feature>();// last_char_cat_1,
																	// tag_1
		m_mapNorSeparateCharCat = new HashMap<String, Feature>();
		m_mapNorConsecutiveCharCat = new HashMap<String, Feature>();

		m_mapNorConsecutiveCharTagCat = new HashMap<String, Feature>();
		m_mapNorSeparateWordCat = new HashMap<String, Feature>();
		m_mapNorTagByCurWordCat = new HashMap<String, Feature>();

		// sense feature templates
		m_mapWordSense = new HashMap<String, Feature>(); // w1s1
		m_mapLastWordAndWordSense = new HashMap<String, Feature>(); // w1s1w0
		m_mapPreWordAndWordSense = new HashMap<String, Feature>(); // w1s1w2
		m_mapStartPreAndWordSense = new HashMap<String, Feature>(); // w1s1statr_w2
		m_mapEndPreAndWordSense = new HashMap<String, Feature>(); // w1s1end_w2
		m_mapStartLastAndWordSense = new HashMap<String, Feature>(); // w1s1start_w0
		m_mapLastCharAndWordSense = new HashMap<String, Feature>(); // w1s1C0
		m_mapTagWordSense = new HashMap<String, Feature>(); // w1s1t1
		m_mapPreTagAndWordSense = new HashMap<String, Feature>(); // w1s1t2
		m_mapLastTagAndWordSense = new HashMap<String, Feature>(); // w1s1t0
		m_mapThreeWordAndSense = new HashMap<String, Feature>(); // w1s1w0w2
		m_mapPreLastTagAndWordSense = new HashMap<String, Feature>(); // w1s1t0t2
		m_mapTwoWordSense = new HashMap<String, Feature>(); // w1s1w2s2
		m_mapLastTagAndTwoWordSense = new HashMap<String, Feature>(); // w1s1w2s2t0
		m_mapLastWordAndTwoWordSense = new HashMap<String, Feature>(); // w1s1w2s2w0
		m_mapLastTagAndPreWordSense = new HashMap<String, Feature>(); // w2s2t0
		m_mapLastWordAndPreWordSense = new HashMap<String, Feature>(); // w2s2w0
		m_mapLastCharAndPreWordSense = new HashMap<String, Feature>(); // w3s2c0

		m_mapGram2 = new HashMap<String, Feature>();
		m_mapGram3 = new HashMap<String, Feature>();
		m_mapGram4 = new HashMap<String, Feature>();
		m_mapOrgIsDisease = new HashMap<String, Feature>();
		m_mapOrgIsPartialDisease = new HashMap<String,Feature>();
		m_mapOrgPOSConsecutiveChars = new HashMap<String,Feature>();
		m_mapOrgPreWordPos = new HashMap<String,Feature>(); 
		
		//POS
		 m_mapOrgTagCharPOS = new HashMap<String, Feature>(); //t_0+ c_0+ p_0
		 m_mapOrgTagPOS = new HashMap<String, Feature>();//t_0 + p_0 
		 m_mapOrgCharPOSTagBigram = new HashMap<String, Feature>();//t_0 + c_0 + p_0 + p_1
		 m_mapOrgCharPOSTagTrigram = new HashMap<String, Feature>(); //t_0 + c_0 + p_0 +p_1 + p_2
		 m_mapOrgTagsPOSBigram = new HashMap<String, Feature>(); //t_0 + t_1 + p_1 + p_0
	     m_mapOrgTagsPOSTrigram = new HashMap<String, Feature>(); // t_0 + t_1 + t_2 + p_0 + p_1 + p_2
		 m_mapOrgTagPOSBigram = new HashMap<String, Feature>(); //t_0 + p_1 + p_0
		 m_mapOrgTagPOSTrigram = new HashMap<String, Feature>(); // t_0  + p_0 + p_1 + p_2
		
		m_mapOrgCharsAndPOSAndTags = new HashMap<String, Feature>();//c_1 + c_0 + p_1 + p_0 + t_1 +t_0
		m_mapOrgConsecutivePOSAndConsecutiveTags = new HashMap<String, Feature>();//p_1 + p_0 +t_1 + t_0
		m_mapOrgConsecutiveCharsAndConsecutivePOS = new HashMap<String, Feature>();//c_1 + c_0 +p_1+p_0
		//prefix, suffix
		m_mapOrgCharPrefix = new HashMap<String, Feature>();
		m_mapOrgCharSuffix = new HashMap<String, Feature>();
		
		m_mapOrgLastCharBrown = new HashMap<String, Feature>();//brown clustering
		m_mapOrgLastCharVector = new HashMap<String, Feature>();
		
	   m_mapOrgIsAbbre = new HashMap<String,Feature>();//abbreviation
		
	   m_mapOrgStemBigram = new HashMap<String,Feature>();	 //stem s_2 + s_1
	   m_mapOrgStemLabel = new HashMap<String,Feature>();	 //stem s_0 + l_0
		
	   m_mapOrgWordAfterWord = new HashMap<String,Feature>(); //bag of words  w0w1	  
	   m_mapOrgBiWordAfterWord = new HashMap<String, Feature>();//w0w1w2
	  
	  
	   m_mapOrgWordLabelAfterWord = new HashMap<String, Feature>();//w_0l_0w1
	   
	   // normal + after w1
	    m_mapNorSeenWordsAfterWord = new HashMap<String, Feature>();//n_1 + w1
	    m_mapNorLastWordByWordAfterWord = new HashMap<String, Feature>();// + n_1 + "_" + n_2 + w1;
		m_mapNorFirstAndLastCharsAfterWord = new HashMap<String, Feature>();// + start_n_1 + end_n_1 + w1;
		m_mapNorLengthByLastCharAfterWord = new HashMap<String, Feature>();// + end_n_1 + len_n_1 + w1;
		m_mapNorLengthByFirstCharAfterWord = new HashMap<String, Feature>();// + start_n_1 + len_n_1 + w1;
		m_mapNorCurrentWordLastCharAfterWord = new HashMap<String, Feature>();// + end_n_2 + "_" + n_1 + w1;
		m_mapNorLastWordByLastCharAfterWord = new HashMap<String, Feature>();// + end_n_2 + end_n_1 + w1;
		m_mapNorLengthByLastWordAfterWord = new HashMap<String, Feature>();// + n_2 + len_n_1 + w1;
		m_mapNorLastLengthByWordAfterWord = new HashMap<String, Feature>();// + len_n_2 + n_1 + w1;
		m_mapNorCurrentTagAfterWord = new HashMap<String, Feature>();// + n_1 + l_1 + w1;
		m_mapNorTagByWordAndPrevCharAfterWord = new HashMap<String, Feature>();// + n_1 + l_1 + end_n_2 + w1;
		m_mapNorTagByLastWordAfterWord = new HashMap<String, Feature>();// + n_1 + l_0 + w1;
		m_mapNorLastTagByWordAfterWord = new HashMap<String, Feature>();// + n_1 + l_2 + w1;
		m_mapNorTagByLastCharAfterWord = new HashMap<String, Feature>();// + end_n_1 + l_1 + w1;
		m_mapNorLastTagAfterWord = new HashMap<String, Feature>();// + l_1 + l_0 + w1;
		m_mapNorTag0Tag1Size1AfterWord = new HashMap<String, Feature>();// + l_1 + l_0 + len_n_1 + w1;
		m_mapNorTag1Tag2Size1AfterWord = new HashMap<String, Feature>();// + l_2 + l_1 + len_n_1 + w1;
		m_mapNorTag0Tag1Tag2Size1AfterWord = new HashMap<String, Feature>();// + l_2 + l_1 + l_0 + len_n_1 + w1;
		m_mapNorLastTwoTagsByTagAfterWord = new HashMap<String, Feature>();// + l_2 + l_1 + l_0 + w1;
		m_mapNorTagByCharAfterWord = new HashMap<String, Feature>();// + l_0 + w_0 + w1;
	    m_mapNorFirstCharBy2TagsAfterWord = new HashMap<String, Feature>();// + l_0 + l_1 + w_0 + w1;
		m_mapNorSeparateCharsAfterWord = new HashMap<String, Feature>();// + w_1 + w_0 + w1;
		m_mapNorLastWordFirstCharAfterWord = new HashMap<String, Feature>();// + n_1 + "_" + w_0 + w1;
		m_mapNorFirstCharLastWordByWordAfterWord = new HashMap<String, Feature>();// + start_n_1 + w_0 + w1;
		m_mapNorTagWordTagAfterWord = new HashMap<String, Feature>();// + l_2 + n_1 + l_0 + w1;
		m_mapNorWordTagTagAfterWord = new HashMap<String, Feature>();// + n_2 + l_1 + l_0 + w1;
		m_mapNorTagByFirstCharAfterWord = new HashMap<String, Feature>();//  start_n_1 + l_1 + w1;
		m_mapNorFirstAndLastCharsAndLabelAfterWord = new HashMap<String, Feature>(); //start_n_1 + end_n_1 + l_1 + w1;
	    //add w1,w2 after w_0
        m_mapNorSeenWordsAfterWords = new HashMap<String, Feature>();// + n_1 + w1 + w2
		m_mapNorCurrentTagAfterWords = new HashMap<String, Feature>();//+ n_1 +l_1+ w1 +w2
		m_mapNorFirstAndLastCharsAndLabelAfterWords = new HashMap<String, Feature>();// + start_n_1 + end_n_1 + l_1 + w1 +w2
		m_mapNorFirstAndLastCharsAfterWords = new HashMap<String, Feature>();// + start_n_1 + end_n_1 + w1 + w2
		m_mapNorLengthByLastCharAfterWords = new HashMap<String, Feature>();// + end_n_1 + len_n_1 + w1 + w2
		m_mapNorLastWordByLastCharAfterWords = new HashMap<String, Feature>();//+ end_n_2 + end_n_1 + w1 + w2
		m_mapNorTagByLastCharAfterWords = new HashMap<String, Feature>();//+ end_n_1 + l_1 + w1 + w2
		m_mapNorLastTagAfterWords = new HashMap<String, Feature>(); //l_1 + w1 + w2
	
	
	}

	public void printWeight(BufferedWriter bw) {
		try {
			String strFea = "";
			for (featureName f : featureName.values()) {
				HashMap<String, Feature> hm = GetFeatureTemplate(f.toString());
				Iterator it = hm.keySet().iterator();
				int n = 0;
				strFea = "";
				while (it.hasNext()) {
					Object key = it.next();
					if ((n + 1) % 5 == 0) {
						n = 0;
						bw.write(strFea + "\r\n");
						strFea = "";
					}
					Feature tempf = hm.get(key);
					strFea += tempf.name + "#" + tempf.weight + "#" + tempf.sum
							+ "#" + tempf.lastUpdateIndex + "#"
							+ tempf.aveWeight + " ";
					n++;
				}
			}
			bw.write(strFea.trim() + "\r\n");
			bw.write("end" + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
