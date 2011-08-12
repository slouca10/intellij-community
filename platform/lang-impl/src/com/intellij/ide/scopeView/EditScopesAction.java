/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intellij.ide.scopeView;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.ide.util.scopeChooser.ScopeChooserConfigurable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;

/**
 * User: anna
 * Date: 27-Jan-2006
 */
public class EditScopesAction extends AnAction implements DumbAware {
  private static final Logger LOG = Logger.getInstance("com.intellij.ide.scopeView.EditScopesAction");

  public EditScopesAction() {
    getTemplatePresentation().setIcon(ScopeViewPane.ICON);
  }

  public void actionPerformed(AnActionEvent e) {
    final DataContext dataContext = e.getDataContext();
    final Project project = PlatformDataKeys.PROJECT.getData(dataContext);
    LOG.assertTrue(project != null);
    final String scopeName = ProjectView.getInstance(project).getCurrentProjectViewPane().getSubId();
    LOG.assertTrue(scopeName != null);
    final ScopeChooserConfigurable scopeChooserConfigurable = new ScopeChooserConfigurable(project);
    ShowSettingsUtil.getInstance().editConfigurable(project, scopeChooserConfigurable, new Runnable(){
      public void run() {
        scopeChooserConfigurable.selectNodeInTree(scopeName);
      }
    });
  }

  @Override
  public void update(AnActionEvent e) {
    super.update(e);
    e.getPresentation().setEnabled(false);
    final DataContext dataContext = e.getDataContext();
    final Project project = PlatformDataKeys.PROJECT.getData(dataContext);
    if (project != null) {
      final String scopeName = ProjectView.getInstance(project).getCurrentProjectViewPane().getSubId();
      if (scopeName != null) {
        e.getPresentation().setEnabled(true);
      }
    }
  }
}
