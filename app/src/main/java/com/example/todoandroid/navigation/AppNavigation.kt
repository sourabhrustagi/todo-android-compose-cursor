package com.example.todoandroid.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoandroid.ui.screens.LoginScreen
import com.example.todoandroid.ui.screens.SignupScreen
import com.example.todoandroid.ui.screens.TodoScreen
import com.example.todoandroid.ui.screens.UserProfileScreen
import com.example.todoandroid.ui.screens.UserFeedbackScreen
import com.example.todoandroid.ui.screens.SettingsScreen
import com.example.todoandroid.ui.screens.PrivacyPolicyScreen
import com.example.todoandroid.ui.screens.TodoStatsScreen
import com.example.todoandroid.ui.screens.AddEditTaskScreen
import com.example.todoandroid.ui.screens.DataBackupSyncScreen
import com.example.todoandroid.ui.screens.AchievementsScreen
import com.example.todoandroid.ui.screens.FAQScreen
import com.example.todoandroid.ui.screens.WelcomeIntroScreen
import com.example.todoandroid.ui.screens.ForgotPasswordScreen
import com.example.todoandroid.ui.screens.ResetPasswordScreen
import com.example.todoandroid.ui.screens.UserTutorialScreen
import com.example.todoandroid.ui.screens.ChangelogScreen
import com.example.todoandroid.viewmodel.AuthViewModel
import com.example.todoandroid.viewmodel.TodoViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    
    // Debug logging
    androidx.compose.runtime.LaunchedEffect(isLoggedIn) {
        android.util.Log.d("AppNavigation", "Login state changed: $isLoggedIn")
    }
    
    val startDestination = if (isLoggedIn) "main" else "welcome"
    android.util.Log.d("AppNavigation", "Starting with destination: $startDestination")
    
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("welcome") {
            WelcomeIntroScreen(
                onSkip = {
                    navController.navigate("login") {
                        popUpTo("welcome") { inclusive = true }
                    }
                },
                onGetStarted = {
                    navController.navigate("login") {
                        popUpTo("welcome") { inclusive = true }
                    }
                },
                onSignIn = {
                    navController.navigate("login") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            )
        }
        
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onSignupClick = {
                    navController.navigate("signup")
                },
                onForgotPasswordClick = {
                    navController.navigate("forgot_password")
                }
            )
        }
        
        composable("signup") {
            SignupScreen(
                onBack = {
                    navController.popBackStack()
                },
                onSignupSuccess = {
                    navController.navigate("login") {
                        popUpTo("signup") { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate("login") {
                        popUpTo("signup") { inclusive = true }
                    }
                }
            )
        }
        
        composable("main") {
            TodoScreen(
                onProfileClick = {
                    navController.navigate("profile")
                },
                onSettingsClick = {
                    navController.navigate("settings")
                },
                onStatsClick = {
                    navController.navigate("stats")
                },
                onLogoutClick = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("main") { inclusive = true }
                    }
                },
                onFeedbackClick = {
                    navController.navigate("feedback")
                },
                onFAQClick = {
                    navController.navigate("faq")
                },
                onAchievementsClick = {
                    navController.navigate("achievements")
                },
                onChangelogClick = {
                    navController.navigate("changelog")
                },
                onBackupSyncClick = {
                    navController.navigate("backup_sync")
                },
                onPrivacyPolicyClick = {
                    navController.navigate("privacy")
                },
                onAddTaskClick = {
                    navController.navigate("add_task")
                }
            )
        }
        
        composable("profile") {
            UserProfileScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("profile") { inclusive = true }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("feedback") {
            UserFeedbackScreen(
                onBack = {
                    navController.popBackStack()
                },
                onFeedbackSubmitted = { feedbackData ->
                    // Handle feedback submission
                    // For now, just show success dialog
                }
            )
        }
        
        composable("settings") {
            SettingsScreen(
                onBack = {
                    navController.popBackStack()
                },
                onPrivacyPolicyClick = {
                    navController.navigate("privacy")
                },
                onBackupSyncClick = {
                    navController.navigate("backup_sync")
                },
                onAchievementsClick = {
                    navController.navigate("achievements")
                },
                onFAQClick = {
                    navController.navigate("faq")
                },
                onTutorialClick = {
                    navController.navigate("tutorial")
                },
                onChangelogClick = {
                    navController.navigate("changelog")
                }
            )
        }
        
        composable("privacy") {
            PrivacyPolicyScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("stats") {
            TodoStatsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // New screens routes
        composable("add_task") {
            val todoViewModel: TodoViewModel = hiltViewModel()
            AddEditTaskScreen(
                onBack = {
                    navController.popBackStack()
                },
                onSave = { title, category, isImportant, dueDate ->
                    // Add the todo using TodoViewModel
                    todoViewModel.addTodo(title, category, isImportant)
                    navController.popBackStack()
                }
            )
        }
        
        composable("edit_task/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toLongOrNull()
            val todoViewModel: TodoViewModel = hiltViewModel()
            AddEditTaskScreen(
                todo = null, // TODO: Get task by ID
                onBack = {
                    navController.popBackStack()
                },
                onSave = { title, category, isImportant, dueDate ->
                    // Edit the todo using TodoViewModel
                    taskId?.let { id ->
                        todoViewModel.editTodo(id, title, category, isImportant)
                    }
                    navController.popBackStack()
                }
            )
        }
        
        composable("backup_sync") {
            DataBackupSyncScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("achievements") {
            AchievementsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("faq") {
            FAQScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("forgot_password") {
            ForgotPasswordScreen(
                onBack = {
                    navController.popBackStack()
                },
                onResetPassword = { email ->
                    // Handle password reset
                },
                onSignInClick = {
                    navController.navigate("login") {
                        popUpTo("forgot_password") { inclusive = true }
                    }
                }
            )
        }
        
        composable("reset_password") {
            ResetPasswordScreen(
                onBack = {
                    navController.popBackStack()
                },
                onPasswordReset = { newPassword, confirmPassword ->
                    // Handle password reset
                    navController.navigate("login") {
                        popUpTo("reset_password") { inclusive = true }
                    }
                },
                onSignInClick = {
                    navController.navigate("login") {
                        popUpTo("reset_password") { inclusive = true }
                    }
                }
            )
        }
        
        composable("tutorial") {
            UserTutorialScreen(
                onBack = {
                    navController.popBackStack()
                },
                onComplete = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("changelog") {
            ChangelogScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}


