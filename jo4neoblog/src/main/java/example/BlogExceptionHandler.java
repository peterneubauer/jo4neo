package example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.neo4j.graphdb.NotFoundException;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.exception.DefaultExceptionHandler;

public class BlogExceptionHandler extends DefaultExceptionHandler {
	

	    public Resolution handleGeneric(NotFoundException exc, HttpServletRequest request, HttpServletResponse response) {
	        return new ForwardResolution("/page_not_found.jsp");
	    }
	}

