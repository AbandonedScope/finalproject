package com.mahanko.finalproject.tag;

import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

/**
 * The type FooterTag class.
 */
public class FooterTag extends TagSupport {

    @Override
    public int doStartTag() throws JspTagException {
        try{
            JspWriter out = pageContext.getOut();
            String tagText = "<footer class=\"bg-light footer fixed-bottom ps-5 pt-2\"><p>© 2021-2022 Copyright by Maxim Mahanko</p></footer>";
            out.write(tagText);
        } catch (IOException e) {
            throw new JspTagException(e);
        }
        return SKIP_BODY;
    }
}