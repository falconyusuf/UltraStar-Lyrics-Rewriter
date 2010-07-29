/*
 * USLRView.java
 */

package uslr;

import java.awt.event.KeyEvent;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLEditorKit;

/**
 * The application's main frame.
 */
public class USLRView extends FrameView {

    private class EditLyricsKeyListener implements KeyListener {

        public EditLyricsKeyListener(USLRView view) {
            this.view = view;
        }

        public void keyPressed(KeyEvent e) {
            
            if(e.isControlDown()) {
                switch(e.getKeyCode()) {
                    // Get char
                    case KeyEvent.VK_Z:
                        view.lyricsPaneGetChar();
                        e.consume();
                        break;
                    // Skip char
                    case KeyEvent.VK_X:
                        view.lyricsPaneSkipChar();
                        e.consume();
                        break;
                    // Previous syllable
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        view.lyricsPanePreviousSyllable();
                        e.consume();
                        break;
                    // Next syllable
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_RIGHT:
                        view.lyricsPaneNextSyllable();
                        e.consume();
                        break;
                    // Previous line
                    case KeyEvent.VK_UP:
                        view.lyricsPanePreviousLine();
                        e.consume();
                        break;
                    // Next line
                    case KeyEvent.VK_DOWN:
                        view.lyricsPaneNextLine();
                        e.consume();
                        break;
                }
            }
        }

        public void keyTyped(KeyEvent e) {

        }

        public void keyReleased(KeyEvent e) {

        }

        USLRView view;
    }

    public USLRView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });

        editLyricsField.addKeyListener(new EditLyricsKeyListener(this));

        currentLineField.setEditorKit(new HTMLEditorKit());
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = USLRApp.getApplication().getMainFrame();
            aboutBox = new USLRAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        USLRApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        lyricsPanel = new javax.swing.JPanel();
        editLyricsField = new javax.swing.JTextField();
        prevSyllableButton = new javax.swing.JButton();
        nextSyllableButton = new javax.swing.JButton();
        getCharButton = new javax.swing.JButton();
        skipCharButton = new javax.swing.JButton();
        newLyricsScrollPane = new javax.swing.JScrollPane();
        newLyricsTextArea = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        currentLineField = new javax.swing.JEditorPane();
        logPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        logTextArea = new javax.swing.JTextArea();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        toolsMenu = new javax.swing.JMenu();
        createSongListItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        lyricsPanel.setName("lyricsPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(uslr.USLRApp.class).getContext().getResourceMap(USLRView.class);
        editLyricsField.setText(resourceMap.getString("editLyricsField.text")); // NOI18N
        editLyricsField.setName("editLyricsField"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(uslr.USLRApp.class).getContext().getActionMap(USLRView.class, this);
        prevSyllableButton.setAction(actionMap.get("lyricsPanePreviousSyllable")); // NOI18N
        prevSyllableButton.setText(resourceMap.getString("prevSyllableButton.text")); // NOI18N
        prevSyllableButton.setName("prevSyllableButton"); // NOI18N

        nextSyllableButton.setAction(actionMap.get("lyricsPaneNextSyllable")); // NOI18N
        nextSyllableButton.setText(resourceMap.getString("nextSyllableButton.text")); // NOI18N
        nextSyllableButton.setName("nextSyllableButton"); // NOI18N

        getCharButton.setAction(actionMap.get("lyricsPaneGetChar")); // NOI18N
        getCharButton.setText(resourceMap.getString("getCharButton.text")); // NOI18N
        getCharButton.setName("getCharButton"); // NOI18N

        skipCharButton.setAction(actionMap.get("lyricsPaneSkipChar")); // NOI18N
        skipCharButton.setText(resourceMap.getString("skipCharButton.text")); // NOI18N
        skipCharButton.setMaximumSize(new java.awt.Dimension(106, 27));
        skipCharButton.setMinimumSize(new java.awt.Dimension(106, 27));
        skipCharButton.setName("skipCharButton"); // NOI18N

        newLyricsScrollPane.setName("newLyricsScrollPane"); // NOI18N

        newLyricsTextArea.setColumns(20);
        newLyricsTextArea.setRows(5);
        newLyricsTextArea.setName("newLyricsTextArea"); // NOI18N
        newLyricsScrollPane.setViewportView(newLyricsTextArea);

        jButton1.setAction(actionMap.get("lyricsPaneNextLine")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        currentLineField.setEditable(false);
        currentLineField.setEditorKit(null);
        currentLineField.setName("currentLineField"); // NOI18N
        jScrollPane2.setViewportView(currentLineField);

        javax.swing.GroupLayout lyricsPanelLayout = new javax.swing.GroupLayout(lyricsPanel);
        lyricsPanel.setLayout(lyricsPanelLayout);
        lyricsPanelLayout.setHorizontalGroup(
            lyricsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lyricsPanelLayout.createSequentialGroup()
                .addGroup(lyricsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lyricsPanelLayout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(prevSyllableButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editLyricsField, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nextSyllableButton, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(lyricsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(lyricsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(newLyricsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                            .addGroup(lyricsPanelLayout.createSequentialGroup()
                                .addComponent(getCharButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(skipCharButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE))))
                .addContainerGap())
        );
        lyricsPanelLayout.setVerticalGroup(
            lyricsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lyricsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(lyricsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editLyricsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prevSyllableButton)
                    .addComponent(nextSyllableButton)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lyricsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(getCharButton)
                    .addComponent(skipCharButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newLyricsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(resourceMap.getString("lyricsPanel.TabConstraints.tabTitle"), lyricsPanel); // NOI18N

        logPanel.setName("logPanel"); // NOI18N
        logPanel.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        logTextArea.setColumns(20);
        logTextArea.setRows(5);
        logTextArea.setName("logTextArea"); // NOI18N
        jScrollPane1.setViewportView(logTextArea);

        logPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(resourceMap.getString("logPanel.TabConstraints.tabTitle"), logPanel); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        openMenuItem.setAction(actionMap.get("openSongAction")); // NOI18N
        openMenuItem.setText(resourceMap.getString("openMenuItem.text")); // NOI18N
        openMenuItem.setName("openMenuItem"); // NOI18N
        fileMenu.add(openMenuItem);

        jMenuItem1.setAction(actionMap.get("saveSongAction")); // NOI18N
        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        fileMenu.add(jMenuItem1);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        toolsMenu.setText(resourceMap.getString("toolsMenu.text")); // NOI18N
        toolsMenu.setName("toolsMenu"); // NOI18N

        createSongListItem.setAction(actionMap.get("createSongList")); // NOI18N
        createSongListItem.setText(resourceMap.getString("createSongListItem.text")); // NOI18N
        createSongListItem.setName("createSongListItem"); // NOI18N
        toolsMenu.add(createSongListItem);

        menuBar.add(toolsMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 291, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void openSongAction() {
        JFrame mainFrame = USLRApp.getApplication().getMainFrame();
        int returnVal = fileChooser.showOpenDialog(mainFrame);
        if(returnVal != JFileChooser.APPROVE_OPTION)
            return;
        
        loadedFile = fileChooser.getSelectedFile();
        try {
            log("Opening song: " + loadedFile.getCanonicalPath());
            loadSong();
        }
        catch(Exception x) {
            log(x.getLocalizedMessage() + " in " + loadedFile);
        }
     }

    public void log(String text) {
        logTextArea.append(text + "\n");
    }

    public void loadSong() throws Exception {
        loadedSong = new Song(loadedFile);
        setCurrentSyllable(loadedSong.getFirstSyllable());
    }

    @Action
    public void loadLyricsFileAction() {
        JFrame mainFrame = USLRApp.getApplication().getMainFrame();
        int returnVal = fileChooser.showOpenDialog(mainFrame);
        loadedFile = fileChooser.getSelectedFile();
        try {
            log("Loading lyrics file: " + loadedFile.getCanonicalPath());
            loadLyricsFile(loadedFile);
        }
        catch(Exception x) {
            log(x.getLocalizedMessage());
        }
    }

    public void loadLyricsFile(File lyricsFile) {
        try {
            FileInputStream strm = new FileInputStream(lyricsFile);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(strm, "UTF-8"));
            String lyrics = "";
            String line = reader.readLine();
            while(line != null) {
                lyrics += line + " ";
                line = reader.readLine();
            }
            strm.close();

            newLyricsTextArea.setText(lyrics);
        }
        catch(Exception x) {
            log("Exception: " + x.getLocalizedMessage());
        }
    }

    public void setCurrentSyllable(LyricsSyllable syllable) {
        this.currentSyllable = syllable;
        LyricsLine line = syllable.getLine();
        int sylIdx = line.getCharIndex(syllable);
        currentLineField.setText(line.getLyrics(syllable));
        //currentLineField.select(sylIdx, sylIdx + syllable.getLyrics().length());
        editLyricsField.setText(syllable.getLyrics());
        editLyricsField.selectAll();
        editLyricsField.requestFocusInWindow();
    }

    public void eraseSelectedText() {
        int selStart = editLyricsField.getSelectionStart();
        int selEnd = editLyricsField.getSelectionEnd();
        String text = editLyricsField.getText();
        editLyricsField.setText(
                text.substring(0, selStart) +
                text.substring(selEnd, text.length()));
    }

    @Action
    public void lyricsPaneGetChar() {
        editLyricsField.requestFocusInWindow();
        if(newLyricsTextArea.getText().length() < 1) return;
        try {
            String gotten = newLyricsTextArea.getText(0, 1);
            eraseSelectedText();
            editLyricsField.setText(
                    editLyricsField.getText() + gotten);
            newLyricsTextArea.setText(newLyricsTextArea.getText().substring(1));
            while(newLyricsTextArea.getText().length() > 0
                    && newLyricsTextArea.getText(0, 1).equals("\n")) {
                newLyricsTextArea.setText(newLyricsTextArea.getText().substring(1));
            }
            newLyricsTextArea.setCaretPosition(0);
        }
        catch(BadLocationException x) {

        }
    }

    @Action
    public void lyricsPaneSkipChar() {
        editLyricsField.requestFocusInWindow();
        if(newLyricsTextArea.getText().length() < 1) return;
        try {
            newLyricsTextArea.setText(newLyricsTextArea.getText().substring(1));
            while(newLyricsTextArea.getText().length() > 0
                    && newLyricsTextArea.getText(0, 1).equals("\n")) {
                newLyricsTextArea.setText(newLyricsTextArea.getText().substring(1));
            }
            newLyricsTextArea.setCaretPosition(0);
        }
        catch(BadLocationException x) {

        }
    }

    @Action
    public void lyricsPaneNextSyllable() {
        if(currentSyllable == null) return;
        try {
            currentSyllable.setLyrics(editLyricsField.getText());
            setCurrentSyllable(loadedSong.getNextSyllable(currentSyllable));
        }
        catch(Exception x) {
            log("Exception:" + x.getMessage());
        }
    }

    @Action
    public void lyricsPanePreviousSyllable() {
        if(currentSyllable == null) return;
        try {
            currentSyllable.setLyrics(editLyricsField.getText());
            setCurrentSyllable(loadedSong.getPreviousSyllable(currentSyllable));
        }
        catch(Exception x) {
            log("Exception:" + x.getMessage());
        }
    }

    @Action
    public void saveSongAction() {
        if(loadedSong == null) return;
        JFrame mainFrame = USLRApp.getApplication().getMainFrame();
        int returnVal = fileChooser.showSaveDialog(mainFrame);
        if(returnVal != JFileChooser.APPROVE_OPTION)
            return;
        File targetFile = fileChooser.getSelectedFile();
        try {
            log("Saving song as: " + targetFile.getCanonicalPath());
            loadedSong.save(targetFile);
        }
        catch(Exception x) {
            log("Error while saving: " + x.getLocalizedMessage());
        }
    }

    @Action
    public void lyricsPanePreviousLine() {
        if(currentSyllable == null) return;
        try {
            currentSyllable.setLyrics(editLyricsField.getText());
            LyricsLine line = loadedSong.getPreviousLine(currentSyllable.getLine());
            if(line != null) {
                setCurrentSyllable(line.getFirstSyllable());
            }
        }
        catch(Exception x) {
            log("Exception:" + x.getMessage());
        }
    }

    @Action
    public void lyricsPaneNextLine() {
        if(currentSyllable == null) return;
        try {
            currentSyllable.setLyrics(editLyricsField.getText());
            LyricsLine line = loadedSong.getNextLine(currentSyllable.getLine());
            if(line != null) {
                setCurrentSyllable(line.getFirstSyllable());
            }
        }
        catch(Exception x) {
            log("Exception:" + x.getMessage());
        }
    }

    @Action
    public void createSongList() {
        SongListDialog dialog = new SongListDialog(this.getFrame(), false);
        dialog.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem createSongListItem;
    private javax.swing.JEditorPane currentLineField;
    private javax.swing.JTextField editLyricsField;
    private javax.swing.JButton getCharButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel logPanel;
    private javax.swing.JTextArea logTextArea;
    private javax.swing.JPanel lyricsPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JScrollPane newLyricsScrollPane;
    private javax.swing.JTextArea newLyricsTextArea;
    private javax.swing.JButton nextSyllableButton;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JButton prevSyllableButton;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton skipCharButton;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JMenu toolsMenu;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;

    private final JFileChooser fileChooser = new JFileChooser("/home/sebastian/arbeit/ultrastar/");
    private File loadedFile = null;
    private Song loadedSong = null;

    private LyricsSyllable currentSyllable = null;
}
