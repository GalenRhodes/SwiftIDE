package com.projectgalen.swift.ide.utils;

// ===========================================================================
//     PROJECT: SwiftIDE
//    FILENAME: IDEProperties.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: January 28, 2023
//
// Copyright © 2023 Project Galen. All rights reserved.
//
// Permission to use, copy, modify, and distribute this software for any
// purpose with or without fee is hereby granted, provided that the above
// copyright notice and this permission notice appear in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
// WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
// SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
// ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
// IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
// ===========================================================================

import com.projectgalen.lib.utils.PGProperties;
import com.projectgalen.swift.ide.Main;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiFunction;

public class IDEProperties extends PGProperties {

    private static final String USER_SETTINGS_FILENAME = "user_settings.xml";

    public IDEProperties(@Nullable Properties defaults) { }

    public static IDEProperties getInstance() {
        PGProperties  base  = PGProperties.getXMLProperties("ide_settings.xml", Main.class);
        IDEProperties props = new IDEProperties(base);

        try(InputStream inputStream = new FileInputStream(new File(USER_SETTINGS_FILENAME))) {
            props.loadFromXML(inputStream);
            props.storeToXML(System.out, null, StandardCharsets.UTF_8);
        }
        catch(Throwable throwable) {
            // Ignore
        }

        return props;
    }

    public synchronized void writeSettings() {
        try(OutputStream outputStream = new FileOutputStream(new File(USER_SETTINGS_FILENAME))) {
            storeToXML(outputStream, null, StandardCharsets.UTF_8);
        }
        catch(Throwable throwable) {
            // Ignore
        }
    }

    public void setProperties(@NotNull Map<String, String> props) {
        putAll(props);
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        Object o = super.put(key, value);
        writeSettings();
        return o;
    }

    @Override
    public synchronized Object remove(Object key) {
        Object o = super.remove(key);
        writeSettings();
        return o;
    }

    @Override
    public synchronized void putAll(Map<?, ?> t) {
        super.putAll(t);
        writeSettings();
    }

    @Override
    public synchronized void replaceAll(BiFunction<? super Object, ? super Object, ?> function) {
        super.replaceAll(function);
        writeSettings();
    }

    @Override
    public synchronized Object putIfAbsent(Object key, Object value) {
        Object o = super.putIfAbsent(key, value);
        writeSettings();
        return o;
    }

    @Override
    public synchronized boolean remove(Object key, Object value) {
        boolean b = super.remove(key, value);
        writeSettings();
        return b;
    }

    @Override
    public synchronized boolean replace(Object key, Object oldValue, Object newValue) {
        boolean b = super.replace(key, oldValue, newValue);
        writeSettings();
        return b;
    }

    @Override
    public synchronized Object replace(Object key, Object value) {
        Object o = super.replace(key, value);
        writeSettings();
        return o;
    }

    @Override
    public synchronized Object merge(Object key, Object value, BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        Object o = super.merge(key, value, remappingFunction);
        writeSettings();
        return o;
    }
}
