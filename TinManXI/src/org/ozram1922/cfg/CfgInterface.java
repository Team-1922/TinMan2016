package org.ozram1922.cfg;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

//have children inherit from this class, and have an instance of "ConfigurableClass"
public interface CfgInterface
{
  boolean DeserializeInternal();
  void SerializeInternal(Document doc);
  ConfigurableClass GetCfgClass();
}
