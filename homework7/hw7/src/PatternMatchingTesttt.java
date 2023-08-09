import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import static org.junit.Assert.*;

public class PatternMatchingTesttt {
    private static final int TIMEOUT = 200;

    //initial tests handle edge cases and examples from lectures

    @Test(timeout = TIMEOUT)
    public void bruteForceOneMatch() {
        CharSequence text = "I hate CS1332";
        CharSequence pattern = "hate";
        CharacterComparator comparator = new CharacterComparator();

        assertThrows(IllegalArgumentException.class, () -> PatternMatching.bruteForce(null,  text, comparator));
        assertThrows(IllegalArgumentException.class, () -> PatternMatching.bruteForce("",  text, comparator));
        assertThrows(IllegalArgumentException.class, () -> PatternMatching.bruteForce(pattern,  null, comparator));
        assertThrows(IllegalArgumentException.class, () -> PatternMatching.bruteForce(pattern,  text, null));

        PatternMatching.bruteForce(pattern, "", comparator);
        assertEquals(0, comparator.getComparisonCount());
        List<Integer> list = PatternMatching.bruteForce(pattern, text, comparator);
        assertEquals((Integer) 2, list.get(0));
        assertEquals(13, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void bruteForceNoMatch1() {
        CharSequence text = "I hate CS1332";
        CharSequence pattern = "love";
        CharacterComparator comparator = new CharacterComparator();

        List<Integer> list = PatternMatching.bruteForce(pattern, text, comparator);
        assertEquals(new LinkedList<>(), list);
        assertEquals(10, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void bruteForceNoMatch2() {
        CharSequence text = "abcdef asdknaj abcdef";
        CharSequence pattern = "abcdf";
        CharacterComparator comparator = new CharacterComparator();

        List<Integer> list = PatternMatching.bruteForce(pattern, text, comparator);
        assertEquals(new LinkedList<>(), list);
        assertEquals(27, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void bruteForceMatchAtEnds() {
        CharSequence text = "abcdef asdknaj abcdef";
        CharSequence pattern = "abcdef";
        CharacterComparator comparator = new CharacterComparator();

        LinkedList<Integer> ll = new LinkedList<>(Arrays.asList((Integer) 0, (Integer) 15));
        List<Integer> list = PatternMatching.bruteForce(pattern, text, comparator);
        assertEquals(ll, list);
        assertEquals(28, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void bruteForceSingleCharPattern() {
        CharSequence text = "abcdef asdknaj abcdef";
        CharSequence pattern = "a";
        CharacterComparator comparator = new CharacterComparator();

        LinkedList<Integer> ll = new LinkedList<>(Arrays.asList((Integer) 0, (Integer) 7, (Integer) 12, (Integer) 15));
        List<Integer> list = PatternMatching.bruteForce(pattern, text, comparator);
        assertEquals(ll, list);
        assertEquals(21, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void bruteForceSameTextAndPattern() {
        CharSequence text = "Johnny";
        CharSequence pattern = "Johnny";
        CharacterComparator comparator = new CharacterComparator();

        LinkedList<Integer> ll = new LinkedList<>(Arrays.asList((Integer) 0));
        List<Integer> list = PatternMatching.bruteForce(pattern, text, comparator);
        assertEquals(ll, list);
        assertEquals(6, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void failureTableErrors() {
        assertThrows(IllegalArgumentException.class, () -> PatternMatching.buildFailureTable(null, new CharacterComparator()));
        assertThrows(IllegalArgumentException.class, () -> PatternMatching.buildFailureTable("abc", null));
    }

    @Test(timeout = TIMEOUT)
    public void failureTableEmptyPatternTest() {
        CharSequence pattern = "";
        CharacterComparator comparator = new CharacterComparator();

        int[] failureTable = PatternMatching.buildFailureTable(pattern, comparator);
        assertEquals((new int[0]).length, failureTable.length);
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void failureTableSingleCharTest() {
        CharSequence pattern = "a";
        CharacterComparator comparator = new CharacterComparator();

        int[] failureTable = PatternMatching.buildFailureTable(pattern, comparator);
        assertEquals(1, failureTable.length);
        assertEquals(0, failureTable[0]);
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void failureTableRepetitiveCharTest() {
        CharSequence pattern = "aaaaa";
        CharacterComparator comparator = new CharacterComparator();

        int[] failureTable = PatternMatching.buildFailureTable(pattern, comparator);
        assertEquals(5, failureTable.length);
        assertEquals(0, failureTable[0]);
        assertEquals(1, failureTable[1]);
        assertEquals(2, failureTable[2]);
        assertEquals(3, failureTable[3]);
        assertEquals(4, failureTable[4]);
        assertEquals(4, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void failureTableWithSimilarPattern() {
        CharSequence pattern = "abcabcd";
        CharacterComparator comparator = new CharacterComparator();


        int[] failureTable = PatternMatching.buildFailureTable(pattern, comparator);
        assertEquals(7, failureTable.length);
        assertEquals(0, failureTable[0]);
        assertEquals(0, failureTable[1]);
        assertEquals(0, failureTable[2]);
        assertEquals(1, failureTable[3]);
        assertEquals(2, failureTable[4]);
        assertEquals(3, failureTable[5]);
        assertEquals(0, failureTable[6]);
        assertEquals(7, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void failureTableWithDegeneratePattern() {
        CharSequence pattern = "abcabcabc";
        CharacterComparator comparator = new CharacterComparator();

        int[] failureTable = PatternMatching.buildFailureTable(pattern, comparator);
        assertEquals(9, failureTable.length);
        assertEquals(0, failureTable[0]);
        assertEquals(0, failureTable[1]);
        assertEquals(0, failureTable[2]);
        assertEquals(1, failureTable[3]);
        assertEquals(2, failureTable[4]);
        assertEquals(3, failureTable[5]);
        assertEquals(4, failureTable[6]);
        assertEquals(5, failureTable[7]);
        assertEquals(6, failureTable[8]);
        assertEquals(8, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void KMPErrorTest() {
        CharSequence text = "I hate CS1332";
        CharSequence pattern = "hate";
        CharacterComparator comparator = new CharacterComparator();

        assertThrows(IllegalArgumentException.class, () -> PatternMatching.kmp(null,  text, comparator));
        assertThrows(IllegalArgumentException.class, () -> PatternMatching.kmp("",  text, comparator));
        assertThrows(IllegalArgumentException.class, () -> PatternMatching.kmp(pattern,  null, comparator));
        assertThrows(IllegalArgumentException.class, () -> PatternMatching.kmp(pattern,  text, null));
    }

    @Test(timeout = TIMEOUT)
    public void KMPSinglePatternTest() {
        CharSequence pattern = "a";
        CharSequence text  = "I hate CS 1332";
        CharacterComparator comparator = new CharacterComparator();

        List<Integer> list = PatternMatching.kmp(pattern, text, comparator);
        assertEquals(1, list.size());
        assertEquals((Integer) 3, list.get(0));
        assertEquals(14, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void KMPRepeatingPatternTest() {
        CharSequence pattern = "abab";
        CharSequence text  = "abaa accabababab abab";
        CharacterComparator comparator = new CharacterComparator();

        List<Integer> list = PatternMatching.kmp(pattern, text, comparator);
        assertEquals(4, list.size());
        assertEquals((Integer) 8, list.get(0));
        assertEquals((Integer) 10, list.get(1));
        assertEquals((Integer) 12, list.get(2));
        assertEquals((Integer) 17, list.get(3));
        assertEquals(29, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void KMPNoPatternTest() {
        CharSequence pattern = "lol";
        CharSequence text  = "abaa accabababab abab";
        CharacterComparator comparator = new CharacterComparator();

        List<Integer> list = PatternMatching.kmp(pattern, text, comparator);
        assertEquals(0, list.size());
        assertEquals(21, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void buildLastTableExceptionsTest() {
        assertThrows(IllegalArgumentException.class, () -> PatternMatching.buildLastTable(null));
        CharSequence pattern = "";
        assertEquals(new HashMap<Character, Integer>(), PatternMatching.buildLastTable(pattern));
    }

    @Test(timeout = TIMEOUT)
    public void lastTableRepeatingPatternsTest() {
        CharSequence pattern = "aaaaaa";

        Map<Character, Integer> map = PatternMatching.buildLastTable(pattern);
        Map<Character, Integer> expectedMap = new HashMap<>();
        expectedMap.put('a', 5);
        assertEquals(expectedMap, map);
        assertEquals(1, map.size());
    }

    @Test(timeout = TIMEOUT)
    public void lastTableRepeatingPatternsTest2() {
        CharSequence pattern = "bab";

        Map<Character, Integer> map = PatternMatching.buildLastTable(pattern);
        Map<Character, Integer> expectedMap = new HashMap<>();
        expectedMap.put('b', 2);
        expectedMap.put('a', 1);
        assertEquals(expectedMap, map);
        assertEquals(2, map.size());
    }

    //A bunch of randomly generated tests
    private CharacterComparator comparator;
    private String patternMatch;
    private String patternNoMatch;
    private String text;
    private ArrayList<Integer> matches;
    private ArrayList<Integer> empty;
    private static boolean skipGalil = false;

    @Before
    public void setUp() {
        comparator = new CharacterComparator();
        patternMatch = "";
        patternNoMatch = "";
        text = "";
        matches = new ArrayList<>();
        empty = new ArrayList<>();
    }

    @BeforeClass
    public static void galilWarn() {
        if (PatternMatching.boyerMooreGalilRule("a", "a", new CharacterComparator()) == null) {
            System.err.println("WARNING: Automatically passing tests for Galil Rule because you did not implement it.");
            skipGalil = true;
        }
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_1() {
        text = "thing casewatertimegovernment-thing.partcaseprogramdaynight study.issue.lotchild";
        patternMatch = "thing.";
        patternNoMatch = "week";
        matches.add(30);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 88, was " + comparator.getComparisonCount(), 88 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 81, was " + comparator.getComparisonCount(), 81 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_2() {
        text = "storyweekword.way.school.programstoryareaschoolweek-homerightfamily weekcountryyearwaterstorylifelifewordlife.state.wordday roomareastatepointcountrycase-rightpartwordstatenightnightword area-place statefamilyissuetimesystemplace";
        patternMatch = "part";
        patternNoMatch = "money";
        matches.add(159);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 233, was " + comparator.getComparisonCount(), 233 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 236, was " + comparator.getComparisonCount(), 236 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_3() {
        text = "life system-issue-wordcompanymonth-studytime.year governmentgovernmentyearchildmonthareapeople.stateprogrammonthlotfamily programbook-roomsystemweek-schoolthingdaypeopleschoolchild statearea";
        patternMatch = "school";
        patternNoMatch = "student";
        matches.add(149);
        matches.add(169);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 199, was " + comparator.getComparisonCount(), 199 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 201, was " + comparator.getComparisonCount(), 201 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_4() {
        text = "group-pointcountry-program placelotplacegovernmentfamilyarea.child-familyprogram.moneystudy wayprogram.wayplace-thing schoolstudycountrygroupmonthyearnumberplace right.rightgroupfact";
        patternMatch = "group";
        patternNoMatch = "book";
        matches.add(0);
        matches.add(136);
        matches.add(173);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 189, was " + comparator.getComparisonCount(), 189 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 183, was " + comparator.getComparisonCount(), 183 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_5() {
        text = "familystudent.montharea.family-storyweekroom-waterfactarea system thingbook factstudent nightword study.timeweekcompanystate studyareastorytimeyearprogram rightmoneystudentstudy.study.month areaissuechildbook-";
        patternMatch = "study.";
        patternNoMatch = "lot";
        matches.add(98);
        matches.add(172);
        matches.add(178);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 220, was " + comparator.getComparisonCount(), 220 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 212, was " + comparator.getComparisonCount(), 212 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_6() {
        text = "schoolpeople casesystem-storystudentmonth.lot-governmentpointwaywaterworldstudylot-right room-way-partwordwater.programnumbermonthlotplacepointpointfamily.time case system";
        patternMatch = "word";
        patternNoMatch = "home";
        matches.add(102);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 176, was " + comparator.getComparisonCount(), 176 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 175, was " + comparator.getComparisonCount(), 175 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_7() {
        text = "numbermonth-factschool programpeople.studentpartthingyear-waterroomweekgroupsystem.yearweekstategovernment.way";
        patternMatch = "school ";
        patternNoMatch = "family";
        matches.add(16);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 114, was " + comparator.getComparisonCount(), 114 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 111, was " + comparator.getComparisonCount(), 111 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_8() {
        text = "area.lothomesystemwaypeopletime-world.company.rightfactpeoplenumbercompany.word-way ";
        patternMatch = "number";
        patternNoMatch = "school";
        matches.add(61);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 86, was " + comparator.getComparisonCount(), 86 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 86, was " + comparator.getComparisonCount(), 86 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_9() {
        text = "issuestudy thingissuestudywaterstudentyearschoolgroupissue issueworldmonth-moneywater.waterplacecompanycountrygroupmonthprogram";
        patternMatch = "issue";
        patternNoMatch = "night";
        matches.add(0);
        matches.add(16);
        matches.add(53);
        matches.add(59);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 128, was " + comparator.getComparisonCount(), 128 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 134, was " + comparator.getComparisonCount(), 134 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_10() {
        text = "areawayissuestate studentwordcountrystatecountryprogram.school.wayarea.companystudentprogramcountrygroup.week.rightprogram nightstate case.numbercasestoryweekissuelifeworldissueweekday booktime numbernight";
        patternMatch = "group.";
        patternNoMatch = "fact";
        matches.add(99);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 210, was " + comparator.getComparisonCount(), 210 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 206, was " + comparator.getComparisonCount(), 206 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_11() {
        text = "factbook countryfactstudentsystem.weekthingfactweek-studydaychildstudystorypointrighttimebookchildsystem-waygroup wordlife.moneyfactlothome-grouplotweekpoint systemworld-weekpartcaselife-part-pointpeople.";
        patternMatch = "lot";
        patternNoMatch = "area";
        matches.add(132);
        matches.add(145);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 210, was " + comparator.getComparisonCount(), 210 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 213, was " + comparator.getComparisonCount(), 213 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_12() {
        text = "weekwater partcompany fact roomdaygovernment part waycountrychildschoolstudymoneypointhome";
        patternMatch = "child";
        patternNoMatch = "people";
        matches.add(60);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 94, was " + comparator.getComparisonCount(), 94 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 95, was " + comparator.getComparisonCount(), 95 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_13() {
        text = "lotstudentprogramnightwaterwaypeoplepart schoolstoryareapoint-familylotyearyearcompanypartroom";
        patternMatch = "area";
        patternNoMatch = "world";
        matches.add(52);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 104, was " + comparator.getComparisonCount(), 104 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 96, was " + comparator.getComparisonCount(), 96 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_14() {
        text = "day.issueprogramstorychildtime";
        patternMatch = "day.";
        patternNoMatch = "system";
        matches.add(0);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 31, was " + comparator.getComparisonCount(), 31 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 34, was " + comparator.getComparisonCount(), 34 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_15() {
        text = "night areastatechildtimelifeschool-lifebookwordschoolbook thingmoneystory.thingfact";
        patternMatch = "area";
        patternNoMatch = "day";
        matches.add(6);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 85, was " + comparator.getComparisonCount(), 85 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 85, was " + comparator.getComparisonCount(), 85 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_16() {
        text = "yearnumber nightplacegroup-family.time-school-way.casesystem.childwatercompanyprogrammoneyschool world";
        patternMatch = "night";
        patternNoMatch = "life";
        matches.add(11);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 105, was " + comparator.getComparisonCount(), 105 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 107, was " + comparator.getComparisonCount(), 107 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_17() {
        text = "day-groupbook placestatefamily";
        patternMatch = "family";
        patternNoMatch = "month";
        matches.add(24);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 35, was " + comparator.getComparisonCount(), 35 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 30, was " + comparator.getComparisonCount(), 30 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_18() {
        text = "story ";
        patternMatch = "story ";
        patternNoMatch = "state";
        matches.add(0);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 11, was " + comparator.getComparisonCount(), 11 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 7, was " + comparator.getComparisonCount(), 7 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_19() {
        text = "daycountrywordpeople-";
        patternMatch = "day";
        patternNoMatch = "way";
        matches.add(0);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 22, was " + comparator.getComparisonCount(), 22 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 22, was " + comparator.getComparisonCount(), 22 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMP_20() {
        text = "story part-company.familymoneyrightcompany.home.countryschoolprogram-waybookcompanyissue moneyplaceplace.groupfamilytimewaterpartfamily-nightcasefamilyareaweeknumbersystemcompanyfamilylotpoint-groupstatestatemonthpoint-areapoint";
        patternMatch = "country";
        patternNoMatch = "child";
        matches.add(48);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.kmp(patternMatch, text, comparator));
        assertTrue("Comparison count should be 236, was " + comparator.getComparisonCount(), 236 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.kmp(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 237, was " + comparator.getComparisonCount(), 237 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_1() {
        text = "factstudentrightstorylottime.nightstudystudywaterprogramwater-school placewaymonthpart-moneystudentright-";
        patternMatch = "place";
        patternNoMatch = "family";
        matches.add(69);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 27, was " + comparator.getComparisonCount(), 27 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 19, was " + comparator.getComparisonCount(), 19 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_2() {
        text = "nightroomgovernmentprogramwaycompany thingsystem.yearthingfamily weekmonthcompanydaymonththing case wordpart-place governmentprogram life systempoint number-water-issueright.yearschoolgrouplifefamilyhomeright.wordlot-lotplace ";
        patternMatch = "group";
        patternNoMatch = "area";
        matches.add(184);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 55, was " + comparator.getComparisonCount(), 55 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 68, was " + comparator.getComparisonCount(), 68 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_3() {
        text = "way wayparttimeplacerightweekroom";
        patternMatch = "way";
        patternNoMatch = "study";
        matches.add(0);
        matches.add(4);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 16, was " + comparator.getComparisonCount(), 16 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 7, was " + comparator.getComparisonCount(), 7 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_4() {
        text = "lotfamilycountry governmenttimeyear-studentprogram programyearyearstorygroup-casewordstate group";
        patternMatch = "word";
        patternNoMatch = "area";
        matches.add(81);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 30, was " + comparator.getComparisonCount(), 30 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 30, was " + comparator.getComparisonCount(), 30 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_5() {
        text = "way system";
        patternMatch = "system";
        patternNoMatch = "point";
        matches.add(4);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 7, was " + comparator.getComparisonCount(), 7 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 2, was " + comparator.getComparisonCount(), 2 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_6() {
        text = "right.programchildstudent area timerightroomgovernmentwordnumberweekchild.moneyhomedayfamilyroom-bookstoryfactstudy-yearstorygroup case";
        patternMatch = "area ";
        patternNoMatch = "point";
        matches.add(26);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 36, was " + comparator.getComparisonCount(), 36 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 35, was " + comparator.getComparisonCount(), 35 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_7() {
        text = "book.fact area state.companything-childyeartimeworld story.book.place.partgroup part waterrightcompanymonthbookfact partchild.companyweekissuestudyissue.areanumberissuestudyschool governmentlot governmentlifecasestudyrightmonthcasenight";
        patternMatch = "fact ";
        patternNoMatch = "family";
        matches.add(5);
        matches.add(111);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 65, was " + comparator.getComparisonCount(), 65 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 43, was " + comparator.getComparisonCount(), 43 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_8() {
        text = "companymoneypeoplefact-countryplacepart-life student numberpeople-day";
        patternMatch = "company";
        patternNoMatch = "government";
        matches.add(0);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 21, was " + comparator.getComparisonCount(), 21 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 8, was " + comparator.getComparisonCount(), 8 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_9() {
        text = "studentroomworld-systemstudyareaprogramstatelife-companyissueprogram-studypartplace roomday systemcompany wordgrouppointstudentchildlifewordcase roomhome room.familypartstate.childareanumbermoneyday wayissuelot";
        patternMatch = "company ";
        patternNoMatch = "book";
        matches.add(98);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 48, was " + comparator.getComparisonCount(), 48 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 57, was " + comparator.getComparisonCount(), 57 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_10() {
        text = "monthchildmonthcasechildright-homecompany-homeplaceprogram familyword.day issue moneylottimeplacegroupcase-word.place day-group.numbercountryyear booksystemrightissueplacepeoplestoryhomepeopleschoolchild companytime.government-";
        patternMatch = "place";
        patternNoMatch = "night";
        matches.add(46);
        matches.add(92);
        matches.add(112);
        matches.add(166);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 75, was " + comparator.getComparisonCount(), 75 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 57, was " + comparator.getComparisonCount(), 57 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_11() {
        text = "child-word.waymoney.casefamilysystemcompany-homeroompart.watergovernmentweek program.moneywaywordwater story companyareacountryschool thing ";
        patternMatch = "week ";
        patternNoMatch = "month";
        matches.add(72);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 33, was " + comparator.getComparisonCount(), 33 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 33, was " + comparator.getComparisonCount(), 33 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_12() {
        text = "roomschool right.righthomecasesystem-place.bookstudy-familyday.systemwordcompanymoneyhomecountrynumberbookmoney.programworldworld fact storychildstateworld.";
        patternMatch = "case";
        patternNoMatch = "week";
        matches.add(26);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 47, was " + comparator.getComparisonCount(), 47 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 42, was " + comparator.getComparisonCount(), 42 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_13() {
        text = "waydaypointgrouparealifemonthissuegovernment worldbook";
        patternMatch = "way";
        patternNoMatch = "company";
        matches.add(0);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 21, was " + comparator.getComparisonCount(), 21 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 10, was " + comparator.getComparisonCount(), 10 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_14() {
        text = "studentyear.room thingfactsystem.studenthome-childareacountrystaterightstudent-studystudyarea governmentmonthright.timething-time homelotworld";
        patternMatch = "time";
        patternNoMatch = "part";
        matches.add(115);
        matches.add(125);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 51, was " + comparator.getComparisonCount(), 51 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 42, was " + comparator.getComparisonCount(), 42 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_15() {
        text = "wordpeoplecasepartschoolareaschoolstudy program.partsystem.nightway.storylotmoney-storycountrygovernment-statepoint worldarea.point";
        patternMatch = "way.";
        patternNoMatch = "student";
        matches.add(64);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 42, was " + comparator.getComparisonCount(), 42 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 26, was " + comparator.getComparisonCount(), 26 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_16() {
        text = "week.placeweekfactweekcountryfact-booktimelifeissueweekdaynumbernight.stateissue.governmentstudent part.life factmonth-weeksystem-moneypartplace government.watergovernment timethingwaterprogramstudentplacecountrypeoplemoneycountry monthnightlotworldrighttimechild factpart ";
        patternMatch = "fact";
        patternNoMatch = "room";
        matches.add(14);
        matches.add(29);
        matches.add(109);
        matches.add(264);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 98, was " + comparator.getComparisonCount(), 98 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 78, was " + comparator.getComparisonCount(), 78 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_17() {
        text = "way-student-homeday.companywaterpartgroupwatertimetimestudy-";
        patternMatch = "time";
        patternNoMatch = "system";
        matches.add(46);
        matches.add(50);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 27, was " + comparator.getComparisonCount(), 27 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 11, was " + comparator.getComparisonCount(), 11 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_18() {
        text = "rightmonthsystemnight companyfact thingfact facttime.wateryear.numberfamilyhomecompanynumber company roomlifeday fact.thingsystem.peopleyear partwatertimeissue.life.childbookweekfamilymonthcompanysystemway";
        patternMatch = "number ";
        patternNoMatch = "story";
        matches.add(86);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 42, was " + comparator.getComparisonCount(), 42 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 49, was " + comparator.getComparisonCount(), 49 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore_19() {
        text = "grouppeopleday.moneyprogrampointfamily year.system-country studyfamilypoint.";
        patternMatch = "point";
        patternNoMatch = "area";
        matches.add(27);
        matches.add(70);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMoore(patternMatch, text, comparator));
        assertTrue("Comparison count should be 28, was " + comparator.getComparisonCount(), 28 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMoore(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 19, was " + comparator.getComparisonCount(), 19 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_1() {
        if (skipGalil) return;
        text = "familywordyearstudentstudygroup";
        patternMatch = "family";
        patternNoMatch = "story";
        matches.add(0);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 15, was "+comparator.getComparisonCount(), 15 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 11, was "+comparator.getComparisonCount(), 11 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_2() {
        if (skipGalil) return;
        text = "day-placestudent lifegovernmentpeopleprogramhomehome home-fact-";
        patternMatch = "day-";
        patternNoMatch = "month";
        matches.add(0);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 22, was "+comparator.getComparisonCount(), 22 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 19, was "+comparator.getComparisonCount(), 19 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_3() {
        if (skipGalil) return;
        text = "issuepeople-governmentthing stateschool-factlife.student-study.rightpartstate pointfamilygroup-familyright night-thing room.timecompany money.student.childcountrysystem ";
        patternMatch = "country";
        patternNoMatch = "program";
        matches.add(155);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 43, was "+comparator.getComparisonCount(), 43 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 32, was "+comparator.getComparisonCount(), 32 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_4() {
        if (skipGalil) return;
        text = "time.nightcase.studysystemwayright issueyearroom.pointword.";
        patternMatch = "time.";
        patternNoMatch = "day";
        matches.add(0);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 23, was "+comparator.getComparisonCount(), 23 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 22, was "+comparator.getComparisonCount(), 22 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_5() {
        if (skipGalil) return;
        text = "word.point issuepeoplewaything-areadayplace.nightroomword yearweekyearnightstoryroom-thinggroup issueprogram";
        patternMatch = "year";
        patternNoMatch = "time";
        matches.add(58);
        matches.add(66);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 44, was "+comparator.getComparisonCount(), 44 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 33, was "+comparator.getComparisonCount(), 33 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_6() {
        if (skipGalil) return;
        text = "countrysystemwordwaterpartplacecompanyright";
        patternMatch = "company";
        patternNoMatch = "home";
        matches.add(31);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 20, was "+comparator.getComparisonCount(), 20 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 16, was "+comparator.getComparisonCount(), 16 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_7() {
        if (skipGalil) return;
        text = "schoolpeoplebook-countrynight-study school area-countryword-weekfactnightnightmoneystudent childpartplacemonth-homecompanyschool-familypart.waterareanumberright story storypartcase.wordpoint-way.numberpointwaystatestory ";
        patternMatch = "right ";
        patternNoMatch = "room";
        matches.add(155);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 56, was "+comparator.getComparisonCount(), 56 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 73, was "+comparator.getComparisonCount(), 73 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_8() {
        if (skipGalil) return;
        text = "thingwater waternumber people-areacountry-studylotsystemareabookpoint groupbook.timeroomfactcompanyissuelifeschoolareastudentpeople bookway factnumbergroup";
        patternMatch = "people-";
        patternNoMatch = "case";
        matches.add(23);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 40, was "+comparator.getComparisonCount(), 40 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 45, was "+comparator.getComparisonCount(), 45 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_9() {
        if (skipGalil) return;
        text = "company";
        patternMatch = "company";
        patternNoMatch = "home";
        matches.add(0);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 13, was "+comparator.getComparisonCount(), 13 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 4, was "+comparator.getComparisonCount(), 4 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_10() {
        if (skipGalil) return;
        text = "wordhome monthsystemrightpeople.casefamily-word.nightstudentwaterbookworldstorytimenightpeopleschoolgroup-familyrightgovernmentright arearight-waterprogramprogram.water week.numberstatepart monthwaymonthplaceprogram.wordfamilyplacemoneyroom";
        patternMatch = "week.";
        patternNoMatch = "lot";
        matches.add(169);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 60, was "+comparator.getComparisonCount(), 60 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 101, was "+comparator.getComparisonCount(), 101 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_11() {
        if (skipGalil) return;
        text = "place-moneygroup moneymoneypeopleway-company.casepeoplerightweek.companysystemstudentpartworldyearhomeissuegovernmentnight.partnight lifestudentwordmonthpoint roomtimewaterpeople.month.people.placecasegroup.study yearwayhomecasecaseroomprogramstudystateweek";
        patternMatch = "money";
        patternNoMatch = "day";
        matches.add(6);
        matches.add(17);
        matches.add(22);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 80, was "+comparator.getComparisonCount(), 80 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 103, was "+comparator.getComparisonCount(), 103 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_12() {
        if (skipGalil) return;
        text = "rightgovernment";
        patternMatch = "right";
        patternNoMatch = "thing";
        matches.add(0);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 11, was "+comparator.getComparisonCount(), 11 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 8, was "+comparator.getComparisonCount(), 8 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_13() {
        if (skipGalil) return;
        text = "place program moneystudenthomelotworldwordcountry-studentstudy room-moneywater grouprightpeoplenightfamilyprogramlot.money.booknightnightroom-studentwordyearwaterwordgovernment-state";
        patternMatch = "night";
        patternNoMatch = "month";
        matches.add(95);
        matches.add(127);
        matches.add(132);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 63, was "+comparator.getComparisonCount(), 63 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 50, was "+comparator.getComparisonCount(), 50 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_14() {
        if (skipGalil) return;
        text = "monthcountrything.story-waystudy weekpeople.water.group.point state lotroomworldfactstudent-booklifeword.way homewordfamilysystem-";
        patternMatch = "state ";
        patternNoMatch = "right";
        matches.add(62);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 38, was "+comparator.getComparisonCount(), 38 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 32, was "+comparator.getComparisonCount(), 32 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_15() {
        if (skipGalil) return;
        text = "arearightright word programnumberwaywater.systemplace-weekrightword study.lifebook.school.monthgroup-book.partpart.company.child-wordwaychildhomeschoolroomcompany.home childschoolfactsystemplace";
        patternMatch = "company.";
        patternNoMatch = "people";
        matches.add(115);
        matches.add(155);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 48, was "+comparator.getComparisonCount(), 48 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 41, was "+comparator.getComparisonCount(), 41 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_16() {
        if (skipGalil) return;
        text = "moneyrightplacewordplacecasechildtimeplacewaycase.issue-systemwayprogramnight-state statelotnumberstatetimepoint timehomecompanylifepeopleareabookstudentcountryprogramroomstudentareapeoplechildtimeareaworldnumber-time-countrychildfactfactpeopleway.place";
        patternMatch = "right";
        patternNoMatch = "government";
        matches.add(5);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 67, was "+comparator.getComparisonCount(), 67 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 50, was "+comparator.getComparisonCount(), 50 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_17() {
        if (skipGalil) return;
        text = "water.schoolcompanyprogram money-familywaterstudent-money-peopledaychild yearweekgroup-nightmonthstudylotcountryyearpointfactschoolpart.caselifeyear-world-program countrypoint thing-statethingwordwaypeople-factsystemplacething ";
        patternMatch = "people";
        patternNoMatch = "number";
        matches.add(58);
        matches.add(199);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 64, was "+comparator.getComparisonCount(), 64 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 47, was "+comparator.getComparisonCount(), 47 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_18() {
        if (skipGalil) return;
        text = "studycasegroup companyroomroomchildthingroom-right";
        patternMatch = "room";
        patternNoMatch = "family";
        matches.add(22);
        matches.add(26);
        matches.add(40);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 31, was "+comparator.getComparisonCount(), 31 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 15, was "+comparator.getComparisonCount(), 15 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_19() {
        if (skipGalil) return;
        text = "schoolgrouppartareapart-familylifething schoolgovernment caseroom waycase.area";
        patternMatch = "thing ";
        patternNoMatch = "time";
        matches.add(34);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 25, was "+comparator.getComparisonCount(), 25 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 24, was "+comparator.getComparisonCount(), 24 >= comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testGalil_20() {
        if (skipGalil) return;
        text = "casenightplacefact-thingmoneygovernmentchild.month-story-point.thingstudenttime state case.weekpart factbookschool-issueway.home student-study-worldstudytimeareacompanyplace.right";
        patternMatch = "time ";
        patternNoMatch = "water";
        matches.add(75);
        comparator = new CharacterComparator();
        assertEquals(matches, PatternMatching.boyerMooreGalilRule(patternMatch, text, comparator));
        assertTrue("Comparison count should be 58, was "+comparator.getComparisonCount(), 58 >= comparator.getComparisonCount());
        comparator = new CharacterComparator();
        assertEquals(empty, PatternMatching.boyerMooreGalilRule(patternNoMatch, text, comparator));
        assertTrue("Comparison count should be 47, was "+comparator.getComparisonCount(), 47 >= comparator.getComparisonCount());
    }
}