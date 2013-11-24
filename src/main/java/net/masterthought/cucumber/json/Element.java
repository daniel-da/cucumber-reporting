package net.masterthought.cucumber.json;

import java.util.ArrayList;
import java.util.List;

import net.masterthought.cucumber.ConfigurationOptions;
import net.masterthought.cucumber.util.Util;

import org.apache.commons.lang.StringUtils;

public class Element {

    private String name;
    private String description;
    private String keyword;
    private Step[] steps;
    private Tag[] tags;

    public Element() {

    }

    public List<Step> getSteps() {
        return Util.getAsList(steps);
    }

    public List<Tag> getTags() {
        return Util.getAsList(tags);
    }

    /**
     * 
     * return the element status depending on step status and configuration
     * 
     * @return
     */
    public Util.Status getStatus() {
    	boolean results = Util.hasStatus(steps, Util.Status.FAILED);
        
        if (!results && ConfigurationOptions.skippedFailsBuild()) {
        	results = Util.hasStatus(steps, Util.Status.SKIPPED);
        }

        if (!results && ConfigurationOptions.undefinedFailsBuild()) {
        	results = Util.hasStatus(steps, Util.Status.UNDEFINED);
        }
        
        return !results? Util.Status.PASSED : Util.Status.FAILED;
    }

	public String getRawName() {
        return name;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getName() {
        List<String> contentString = new ArrayList<String>();

        if (Util.itemExists(keyword)) {
            contentString.add("<span class=\"scenario-keyword\">" + keyword + ": </span>");
        }

        if (Util.itemExists(name)) {
            contentString.add("<span class=\"scenario-name\">" + name + "</span>");
        }

        return Util.itemExists(contentString) ? Util.result(getStatus()) + StringUtils.join(contentString.toArray(), " ") + Util.closeDiv() : "";
    }

    public List<String> getTagList() {
        return Util.getTagNames(tags);
    }

    public boolean hasTags() {
        return Util.itemExists(tags);
    }

    public String getTagsList() {
        String result = "<div class=\"feature-tags\"></div>";
        if (Util.itemExists(tags)) {
            String tagList = StringUtils.join(Util.getTagNames(tags).toArray(), ",");
            result = "<div class=\"feature-tags\">" + tagList + "</div>";
        }
        return result;
    }
    
}
