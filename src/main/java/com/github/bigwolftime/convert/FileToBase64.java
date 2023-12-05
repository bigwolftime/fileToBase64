package com.github.bigwolftime.convert;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;

/**
 * @author liuxin
 * @date 2023/12/4 18:31
 */
public class FileToBase64 extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiFile psiFile = e.getRequiredData(CommonDataKeys.PSI_FILE);

        Messages.showInfoMessage("Mmmmm", psiFile.getText());
    }
}
