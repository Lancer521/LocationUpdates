/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

/*
This was found at:  http://developer.android.com/training/basics/network-ops/xml.html
Adjusted/customized slightly to fit my xml files
 */

package com.google.android.gms.location.sample.locationupdates;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class parses XML feeds from stackoverflow.com.
 * Given an InputStream representation of a feed, it returns a List of entries,
 * where each list element represents a single entry (post) in the XML feed.
 */
public class StackOverflowXmlParser {
    private static final String ns = null;

    public List<Entry> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
        //    parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List<Entry> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Entry> entries = new ArrayList<Entry>();

        /*int eventType = parser.getEventType();
        Log.i("TAG", "The event type is: " + eventType);

        while (eventType != XmlPullParser.START_DOCUMENT) {
            eventType = parser.next();
            Log.i("TAG", "The event type is: " + eventType);
        }*/

    //    parser.require(XmlPullParser.START_TAG, ns, null);
        if(parser.next() == XmlPullParser.START_TAG) {                  //PROBLEM IS HERE
        while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag
                if (name.equals("entry")) {
                    entries.add(readEntry(parser));
                } else {
                    skip(parser);
                }
            }
        }
        return entries;
    }

    // This class represents a single entry (post) in the XML feed.
    // It includes the data members "atop_id," "stop_name," "latitude", and "longitude."
    public static class Entry {
        public final String stop_id;
        public final String stop_name;
        public final String latitude;
        public final String longitude;
        public final String routes;
        public final String minutes;

        private Entry(String stop_id, String stop_name, String var1, String var2) {
            this.stop_id = stop_id;
            this.stop_name = stop_name;
            this.latitude = var1;
            this.longitude = var2;
            this.routes = var1;
            this.minutes = var2;
        }
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them
    // off to their respective &quot;read&quot; methods for processing. Otherwise, skips the tag.
    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "entry");

        String stop_id = null;
        String stop_name = null;
        String latitude = null;
        String longitude = null;

        String routes = null;
        String minutes = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("stop_id")) {
                stop_id = readStopId(parser);
            } else if (name.equals("stop_name")) {
                stop_name = readStopName(parser);
            } else if (name.equals("latitude")) {
                latitude = readLatitude(parser);
            } else if (name.equals("longitude")){
                longitude = readLongitude(parser);
            } else if (name.equals("routes")){
                routes = readRoutes(parser);
            } else if (name.equals("minutes")){
                minutes = readMinutes(parser);
            } else {
                skip(parser);
            }
        }
        if(latitude != null) return new Entry(stop_id, stop_name, latitude, longitude);
        else return new Entry(stop_id, stop_name, routes, minutes);
    }

    // Processes stop_id tags in the feed.
    private String readStopId(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "stop_id");
        String stop_id = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "stop_id");
        return stop_id;
    }

    // Processes stop_name tags in the feed.
    private String readStopName(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "stop_name");
        String stop_name = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "stop_name");
        return stop_name;
    }

    // Processes latitude tags in the feed.
    private String readLatitude(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "latitude");
        String latitude = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "latitude");
        return latitude;
    }

    // Processes longitude tags in the feed.
    private String readLongitude(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "longitude");
        String longitude = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "longitude");
        return longitude;
    }

    // Processes routes tags in the feed.
    private String readRoutes(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "routes");
        String routes = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "routes");
        return routes;
    }

    // Processes minutes tags in the feed.
    private String readMinutes(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "minutes");
        String minutes = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "minutes");
        return minutes;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    // Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
    // if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
    // finds the matching END_TAG (as indicated by the value of "depth" being 0).
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
