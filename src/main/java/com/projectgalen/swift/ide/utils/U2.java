package com.projectgalen.swift.ide.utils;

// ===========================================================================
//     PROJECT: SwiftIDE
//    FILENAME: U2.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: January 29, 2023
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

import com.projectgalen.lib.utils.regex.Regex;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;

public final class U2 {
    private U2() { }

    @NotNull
    public static String getComponentBoundsString(@NotNull Component c) {
        return String.format("(%d, %d, %d, %d)", c.getX(), c.getY(), c.getWidth(), c.getHeight());
    }

    public static void setComponentBounds(@NotNull Component c, @NotNull String boundsStr, int defaultWidth, int defaultHeight) {
        Matcher m = Regex.getMatcher("\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)", boundsStr);
        if(m.matches()) {
            c.setBounds(new Rectangle(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4))));
        }
        else {
            c.setSize(defaultWidth, defaultHeight);
            if(c instanceof JFrame) ((JFrame)c).setLocationRelativeTo(null);
        }
    }
}
