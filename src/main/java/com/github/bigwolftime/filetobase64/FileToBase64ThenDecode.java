package com.github.bigwolftime.filetobase64;

import com.github.bigwolftime.filetobase64.util.Base64Util;
import com.github.bigwolftime.filetobase64.util.FileUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.util.ui.TextTransferable;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.Objects;

/**
 * @author liuxin
 * @date 2023/12/4 18:31
 */
public class FileToBase64ThenDecode extends AnAction {

    private static final Logger LOGGER = Logger.getInstance(FileToBase64ThenDecode.class);


    @Override
    public void actionPerformed(AnActionEvent e) {
        // get selected file
        PsiFile psiFile = e.getRequiredData(CommonDataKeys.PSI_FILE);
        // file reference
        VirtualFile file = psiFile.getVirtualFile();

        String base64Content = null;
        try {
            // get file content
            byte[] fileBytes = FileUtil.getFileContent(file.getPath());
            base64Content = Base64Util.encode(fileBytes);
        } catch (Exception ex) {
            LOGGER.error("read file error", ex);
            Messages.showErrorDialog("file path: " + file.getPath(), "Read File Failed");
        }

        if (Objects.isNull(base64Content)) {
            LOGGER.error("base64 content is null, file path: " + file.getPath());
            Messages.showErrorDialog("file path: " + file.getPath(), "File Base64 Encode Is Null");
            return;
        }

        base64Content = "echo '" + base64Content + "' | base64 -d > /tmp/dec";

        // set clipboard
        CopyPasteManager.getInstance().setContents(new TextTransferable(base64Content));

        // get clipboard content and check if it is equal with content
        boolean checkResult = this.checkClipboardContentIsEqual(base64Content);
        if (!checkResult) {
            Messages.showInfoMessage(base64Content, "Please Manually Copy the Following to the Clipboard");
        }
    }


    /**
     * check clipboard content is equal with content
     * @param content
     * @return
     */
    private boolean checkClipboardContentIsEqual(String content) {
        Transferable clipBoardTransferable = CopyPasteManager.getInstance().getContents();
        if (clipBoardTransferable == null) {
            return false;
        }

        String transferData;
        try {
            transferData = (String) clipBoardTransferable.getTransferData(DataFlavor.stringFlavor);
            return Objects.equals(transferData, content);
        } catch (Exception ex) {
            LOGGER.warn("get clipboard content error", ex);
        }

        return false;
    }
}
