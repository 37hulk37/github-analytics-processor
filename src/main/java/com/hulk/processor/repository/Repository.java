package com.hulk.processor.repository;

import java.time.Instant;

public record Repository(
    long id,
    String name,
    String fullName,
    String description,
    String htmlUrl,
    String language,
    int stargazersCount,
    int forksCount,
    Instant createdAt,
    Instant updatedAt,

    boolean allowForking,
    boolean allowMergeCommit,
    boolean allowRebaseMerge,
    boolean allowSquashMerge,
    boolean deleteBranchOnMerge,
    int watchersCount,
    int size,
    int openIssuesCount,
    int subscribersCount,
    boolean hasIssues,
    boolean hasWiki,
    boolean fork,
    boolean hasDownloads,
    boolean hasPages,
    boolean archived,
    boolean disabled,
    boolean hasProjects,
    boolean isPrivate,
    boolean isTemplate,
    String nodeId,
    String homepage,
    String defaultBranch,
    String pushedAt,
    String visibility,
    boolean isTemplateFlag,

    int commitsCount
) {
}
