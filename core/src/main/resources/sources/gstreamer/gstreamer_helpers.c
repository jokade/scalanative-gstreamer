#include <gst/gst.h>

gpointer snhelper_gst_message_src(GstMessage *msg) {
  return GST_MESSAGE_SRC(msg);
}

const char* snhelper_gst_message_src_name(GstMessage *msg) {
  return GST_MESSAGE_SRC_NAME(msg);
}

gpointer snhelper_gst_get_taglist(GstElement* e, const char* name, int streamId) {
  GstTagList *tags = NULL;
  g_signal_emit_by_name(e,name,streamId,&tags);
  return tags;
}