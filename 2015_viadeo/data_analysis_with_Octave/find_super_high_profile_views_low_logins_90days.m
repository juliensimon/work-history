data = csvread("event_breakdown_by_member_90days.csv");
memberids = data(:,1);
logins=data(:,8);
profiles=data(:,13);
idx1 = find(logins < 1000);
idx2 = find(profiles > 7500);
idx3 = intersect(idx1,idx2);
ids = memberids(idx3);
save high_profile_views_low_logins.txt ids


