package org.ozram1922.cfg;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

/*

This interface should be inherited by all classes which want to utilize the XML configuration loading and saving

*/

public class ConfigurableClass
{
  private Element tmpElement = null;
  private String mUniqueName;
  private CfgInterface mThisClass;

  public ConfigurableClass(String uniqueName, CfgInterface thisClass)
  {
    mUniqueName = uniqueName;
    mThisClass = thisClass;
  }
  public boolean Deserialize(Element thisElement)
  {
    if(thisElement == null)
      return false;

    tmpElement = thisElement;
    return mThisClass.DeserializeInternal();
  }
  public Element Serialize(Document doc)
  {
    if(doc == null)
      return null;

    tmpElement = doc.createElement(mUniqueName);
    mThisClass.SerializeInternal(doc);
    return tmpElement;
  }
  public String GetElementTitle()
  {
    return mUniqueName;
  }

  /*

  These are helper functions for adding attributes and children (all 'name' values must be UNIQUE)

  */

  public void SetAttribute(String name, String value)
  {
    tmpElement.setAttribute(name, value);
  }
  public String GetAttribute(String name)
  {
    return tmpElement.getAttribute(name);
  }
  public void AddChild(Element childElement)
  {
    tmpElement.appendChild(childElement);
  }
  public Node GetNthChild(String name, int num)
  {
    NodeList children = tmpElement.getElementsByTagName(name);
    if(num < 0 || children.getLength() < num - 1)
      return null;

    return children.item(num);
  }
  public NodeList GetChildren(String name)
  {
    return tmpElement.getElementsByTagName(name);
  }
}