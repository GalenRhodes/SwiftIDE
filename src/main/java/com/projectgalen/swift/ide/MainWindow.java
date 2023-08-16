package com.projectgalen.swift.ide;

// ===========================================================================
//     PROJECT: SwiftIDE
//    FILENAME: MainWindow.java
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

import com.projectgalen.lib.utils.Null;
import com.projectgalen.swift.ide.utils.U2;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static com.projectgalen.swift.ide.Main.msgs;
import static com.projectgalen.swift.ide.Main.props;

public class MainWindow extends JFrame {

    private final Map<String, Timer> uiTimers = Collections.synchronizedMap(new TreeMap<>());

    private MainWindow() {
        super(msgs.getString("title.window.main.initial"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        U2.setComponentBounds(this, props.getProperty("main.window.pos", ""), 1024, 768);
        setExtendedState(props.getInt("main.window.state", 0));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) { handleResizeOrMove(e, "resize"); }

            @Override
            public void componentMoved(ComponentEvent e) { handleResizeOrMove(e, "moved"); }
        });

        addWindowStateListener(e -> props.setProperty("main.window.state", Objects.toString(getExtendedState())));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { saveWindowPropertiesOnExit(); }
        });
    }

    public static MainWindow getInstance() {
        return MainWindowInstance.INSTANCE;
    }

    protected void handleResizeOrMove(ComponentEvent componentEvent, @NotNull String timerName) {
        Timer timer = uiTimers.get(timerName);

        if(timer == null) {
            timer = new Timer(250, e -> {
                Null.doIfNotNull(uiTimers.remove(timerName), Timer::stop);
                props.setProperty("main.window.pos", U2.getComponentBoundsString(componentEvent.getComponent()));
            });
            timer.setRepeats(false);
            uiTimers.put(timerName, timer);
        }

        timer.restart();
    }

    protected void saveWindowPropertiesOnExit() {
        Map<String, String> p = new TreeMap<>();
        p.put("main.window.pos", U2.getComponentBoundsString(this));
        p.put("main.window.state", Objects.toString(getExtendedState()));
        props.setProperties(p);
    }

    private static final class MainWindowInstance {
        private static final MainWindow INSTANCE = new MainWindow();
    }
}
